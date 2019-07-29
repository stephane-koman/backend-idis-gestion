package com.idis.gestion.service.impl;

import com.idis.gestion.dao.MouvementRepository;
import com.idis.gestion.entities.DetailsColis;
import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.generator.NumeroAvoirGenerator;
import com.idis.gestion.entities.generator.NumeroFactureGenerator;
import com.idis.gestion.service.ColisService;
import com.idis.gestion.service.MouvementService;
import com.idis.gestion.service.pagination.PageFacture;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;

@Service
@Transactional
public class MouvementServiceImpl implements MouvementService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private ColisService colisService;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader  resourceLoader;

    @Value("classpath:logo.jpg")
    private Resource res;

    @Override
    public Facture saveFacture(Facture f) {
        f.setCreateAt(new Date());
        f.setUpdateAt(new Date());

        Facture facture = mouvementRepository.save(f);

        double montant = montantFacture(facture);

        switch (facture.getTypeFacture().getNomTypeFacture().toLowerCase()) {
            case "facture":
                NumeroFactureGenerator generatorFacture = new NumeroFactureGenerator();
                String numeroFacture = generatorFacture.generate(facture.getId());
                facture.setNumeroFacture(numeroFacture);
                facture.setDebit(montant);
                break;
            case "avoir":
                NumeroAvoirGenerator generatorAvoir = new NumeroAvoirGenerator();
                String numeroAvoir = generatorAvoir.generate(facture.getId());
                facture.setNumeroFacture(numeroAvoir);
                facture.setCredit(montant);
                break;
            default:
                throw new RuntimeException("Cette facture ne peut être enregistréé");
        }

        return facture;
    }

    @Override
    public Facture updateFacture(Facture f) {
        Facture facture = mouvementRepository.getFactureByNumeroFacture(f.getNumeroFacture());
        if (facture == null) throw new RuntimeException("Cette facture n'existe pas");

        double montant = montantFacture(f);

        switch (facture.getTypeFacture().getNomTypeFacture().toLowerCase()) {
            case "facture":
                facture.setDebit(montant);
                break;
            case "avoir":
                facture.setCredit(montant);
                break;
            default:
                throw new RuntimeException("Cette facture ne peut être enregistréé");
        }

        facture.getColis().setDetailsColis(f.getColis().getDetailsColis());
        facture.setUpdateAt(new Date());
        facture.setDateEcheance(f.getDateEcheance());

        return mouvementRepository.save(facture);
    }

    @Override
    public PageFacture listFacture(String numero, String referenceColis, String nomTypeFacture, String codeSite, int enable, Pageable pageable) {
        Page<Facture> factures = mouvementRepository.listFactures(numero, referenceColis, nomTypeFacture, codeSite, enable, pageable);
        PageFacture pageFacture = new PageFacture();
        pageFacture.setFactures(factures.getContent());
        pageFacture.setPage(factures.getNumber());
        pageFacture.setNombreFactures(factures.getNumberOfElements());
        pageFacture.setTotalFactures((int) factures.getTotalElements());
        pageFacture.setTotalPages(factures.getTotalPages());
        return pageFacture;
    }

    @Override
    public List<Facture> findAllFactures(Employe employe, int enable) {
        return mouvementRepository.findAllFactures(employe.getSite().getNomSite(), enable);
    }

    @Override
    public List<Facture> findFacturesByNumeroFacture(String numeroFacture, Employe employe, int enable) {
        return mouvementRepository.findFacturesByNumeroFacture(numeroFacture, employe.getSite().getNomSite(), enable);
    }

    @Override
    public Facture getFactureByNumeroFacture(String numeroFacture) {
        return mouvementRepository.getFactureByNumeroFacture(numeroFacture);
    }

    @Override
    public void disableFacture(Long id, Date date) {
        Facture facture = mouvementRepository.getFactureById(id);
        if (facture == null) throw new RuntimeException("Cette transaction n'existe pas");
        mouvementRepository.disableFacture(id, date);
    }

    @Override
    public void enableFacture(Long id, Date date) {
        Facture facture = mouvementRepository.getFactureById(id);
        if (facture == null) throw new RuntimeException("Cette transaction n'existe pas");
        mouvementRepository.enableFacture(id, date);
    }

    @Override
    public void removeFactureById(Long id) {
        Facture facture = mouvementRepository.getFactureById(id);
        if (facture == null) throw new RuntimeException("Cette transaction n'existe pas");
        mouvementRepository.removeFactureById(id);
    }

    // --------------------------- PRIVATE FUNCTIONS ---------------------------------------

    private double montantFacture(Facture facture) {
        double[] montant = new double[1];
        montant[0] = colisService.updateDetailsColis(facture.getColis().getDetailsColis(), facture.getColis());
        return (1 + facture.getTva().getValeurTva()) * montant[0];
    }

    @Override
    public JasperPrint exportFacturePdf(String numeroFacture) throws SQLException{
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = null;
        try {
            //URL image_url = this.getClass().getResource("/var/www/html/images/logo.jpg");
            //File jrxml = new File("/var/www/html/images/facture.jrxml");
            //InputStream jasperStream = new DataInputStream(new FileInputStream(jrxml));
            //String path = resourceLoader.getResource("classpath:facture.jrxml").getURI().getPath();
            InputStream jasperStream = this.getClass().getResourceAsStream("/report/facture.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport jasperReport = compileReport(design);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("numero_facture", numeroFacture);
            //parameters.put("logo", image_url.getPath());

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

        } catch (JRException e) {
            log.info("Error loading file jrxml");
        }
        conn.close();
        return jasperPrint;
    }
}
