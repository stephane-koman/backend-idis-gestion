package com.idis.gestion.service.impl;

import com.idis.gestion.dao.MouvementRepository;
import com.idis.gestion.dao.ReglementRepository;
import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.Reglement;
import com.idis.gestion.entities.generator.NumeroReglementGenerator;
import com.idis.gestion.service.ReglementService;
import com.idis.gestion.service.pagination.PageReglement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReglementServiceImpl implements ReglementService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private final ReglementRepository reglementRepository;

    private final MouvementRepository mouvementRepository;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public ReglementServiceImpl(ReglementRepository reglementRepository, MouvementRepository mouvementRepository) {
        this.reglementRepository = reglementRepository;
        this.mouvementRepository = mouvementRepository;
    }

    @Override
    public Reglement saveReglement(Reglement r) {

        r.setCreateAt(new Date());
        r.setUpdateAt(new Date());
        r.setCredit(r.getMontantRegle());

        updateMontantFactureRegle(r, r, false);

        Reglement reglement = reglementRepository.save(r);

        NumeroReglementGenerator generatorReglement = new NumeroReglementGenerator();
        String numeroReglement = generatorReglement.generate(reglement.getId());
        reglement.setNumeroReglement(numeroReglement);

        return reglement;
    }

    @Override
    public Reglement updateReglement(Reglement r) {
        Reglement reglement = reglementRepository.getReglementById(r.getId());
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");

        updateMontantFactureRegle(r, reglement, true);

        reglement.setFacture(r.getFacture());
        reglement.setMontantRegle(r.getMontantRegle());
        reglement.setTypeReglement(r.getTypeReglement());
        reglement.setCreateAt(r.getCreateAt());
        reglement.setUpdateAt(new Date());

        return reglementRepository.save(reglement);
    }

    private void updateMontantFactureRegle(Reglement r, Reglement reglement, boolean update){

        double[] montant = new double[1];
        montant[0] = 0;

        Facture f = mouvementRepository.getFactureByNumeroFacture(reglement.getFacture().getNumeroFacture());
        f.getReglements().forEach(rg ->{
            montant[0] += rg.getMontantRegle();
        });

        if(update){
            if(f.getDebit() < montant[0] + r.getMontantRegle() - reglement.getMontantRegle()){
                throw new RuntimeException("Le montant restant à régler est : " + (f.getDebit() - montant[0] + reglement.getMontantRegle()) + " " + f.getDevise().getNomDevise() );
            }
            montant[0] = montant[0] + r.getMontantRegle() - reglement.getMontantRegle();
        }else{
            if(f.getDebit() < montant[0] + r.getMontantRegle()){
                throw new RuntimeException("Le montant restant à régler est : " + (f.getDebit() - montant[0]) + " " + f.getDevise().getNomDevise() );
            }
            montant[0] = montant[0] + r.getMontantRegle();
        }

        f.setMontantFactureRegle(montant[0]);
        mouvementRepository.save(f);
    }

    @Override
    public Reglement getReglementById(Long id) {
        return reglementRepository.getReglementById(id);
    }

    @Override
    public PageReglement listReglements(String nomTypeReglement, String numeroFacture, int enable, Pageable pageable) {
        Page<Reglement> reglements = reglementRepository.findAllReglements(nomTypeReglement, numeroFacture, enable, pageable);
        PageReglement pageReglement = new PageReglement();
        pageReglement.setReglements(reglements.getContent());
        pageReglement.setPage(reglements.getNumber());
        pageReglement.setNombreReglements(reglements.getNumberOfElements());
        pageReglement.setTotalReglements((int) reglements.getTotalElements());
        pageReglement.setTotalPages(reglements.getTotalPages());
        return pageReglement;
    }

    @Override
    public void disableReglement(Long id, Date date) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglementRepository.disableReglement(id, date);
    }

    @Override
    public void enableReglement(Long id, Date date) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglementRepository.enableReglement(id, date);
    }

    @Override
    public void removeReglementById(Long id) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        Facture f = reglement.getFacture();
        f.setMontantFactureRegle(f.getMontantFactureRegle() - reglement.getMontantRegle());
        reglementRepository.removeReglementById(id);
    }

    @Override
    public JasperPrint exportReglementPdf(String numeroReglement) throws SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = null;
        try {
            InputStream jasperStream = this.getClass().getResourceAsStream("/report/reglement.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport jasperReport = compileReport(design);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("numero_reglement", numeroReglement);
            //parameters.put("logo", image_url.getPath());

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

        } catch (JRException e) {
            log.info("Error loading file jrxml");
        }
        conn.close();
        return jasperPrint;
    }
}
