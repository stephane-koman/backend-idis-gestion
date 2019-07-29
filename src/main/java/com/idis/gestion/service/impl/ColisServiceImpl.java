package com.idis.gestion.service.impl;

import com.idis.gestion.dao.*;
import com.idis.gestion.entities.*;
import com.idis.gestion.entities.generator.ReferenceColisGenerator;
import com.idis.gestion.service.ColisService;
import com.idis.gestion.service.DetailsColisService;
import com.idis.gestion.service.pagination.PageColis;
import com.idis.gestion.web.controls.Count;
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
import java.util.*;

import static net.sf.jasperreports.engine.JasperCompileManager.compileReport;

@Service
@Transactional
public class ColisServiceImpl implements ColisService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private DetailsColisService detailsColisService;

    @Autowired
    private EnregistrementColisRepository enregistrementColisRepository;

    @Autowired
    private ExpeditionColisRepository expeditionColisRepository;

    @Autowired
    private ArriveeColisRepository arriveeColisRepository;

    @Autowired
    private ReceptionColisRepository receptionColisRepository;

    @Autowired
    private LivraisonColisRepository livraisonColisRepository;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Colis saveColis(Colis c, String codeSite) {

        Collection<DetailsColis> detailsColis = new ArrayList<>();

        c.setCreateAt(new Date());
        c.setUpdateAt(new Date());

        ReferenceColisGenerator generator = new ReferenceColisGenerator();

        Colis colis = colisRepository.save(c);

        addDetailsColis(c.getDetailsColis(), colis);

        String reference = generator.generate(colis.getId(), codeSite);
        colis.setReference(reference);
        colis.setQrCode(reference);

        EnregistrementColis enregistrementColis = new EnregistrementColis();
        enregistrementColis.setUtilisateur(colis.getUtilisateur());
        enregistrementColis.setDateEnregistrement(new Date());
        enregistrementColis.setDescription(colis.getDescription());
        EnregistrementColis eColis = enregistrementColisRepository.save(enregistrementColis);
        colis.setEnregistrementColis(eColis);

        return colis;
    }

    @Override
    public PageColis listColis(
            String reference,
            String nomClient,
            String nomDestinataire,
            int enable,
            Pageable pageable) {
        Page<Colis> colis = colisRepository.listColis(
                reference,
                nomClient,
                nomDestinataire,
                enable,
                pageable
        );

        PageColis pColis = new PageColis();
        pColis.setColis(colis.getContent());
        pColis.setPage(colis.getNumber());
        pColis.setNombreColis(colis.getNumberOfElements());
        pColis.setTotalColis((int) colis.getTotalElements());
        pColis.setTotalPages(colis.getTotalPages());

        return pColis;
    }

    @Override
    public PageColis sendListColis(String reference, String nomClient, String nomDestinataire, String nomSite, int enable, Pageable pageable) {
        Page<Colis> colis = colisRepository.sendListColis(
                reference,
                nomClient,
                nomDestinataire,
                nomSite,
                enable,
                pageable
        );

        PageColis pColis = new PageColis();
        pColis.setColis(colis.getContent());
        pColis.setPage(colis.getNumber());
        pColis.setNombreColis(colis.getNumberOfElements());
        pColis.setTotalColis((int) colis.getTotalElements());
        pColis.setTotalPages(colis.getTotalPages());

        return pColis;
    }

    @Override
    public PageColis receiveListColis(String reference, String nomClient, String nomDestinataire, String nomSite, int enable, Pageable pageable) {
        Page<Colis> colis = colisRepository.receiveListColis(
                reference,
                nomClient,
                nomDestinataire,
                nomSite,
                enable,
                pageable
        );

        PageColis pColis = new PageColis();
        pColis.setColis(colis.getContent());
        pColis.setPage(colis.getNumber());
        pColis.setNombreColis(colis.getNumberOfElements());
        pColis.setTotalColis((int) colis.getTotalElements());
        pColis.setTotalPages(colis.getTotalPages());

        return pColis;
    }

    @Override
    public PageColis clientListColis(String reference, Long idClient, String nomDestinataire, int enable, Pageable pageable) {
        Page<Colis> colis = colisRepository.clientListColis(
                reference,
                idClient,
                nomDestinataire,
                enable,
                pageable
        );

        PageColis pColis = new PageColis();
        pColis.setColis(colis.getContent());
        pColis.setPage(colis.getNumber());
        pColis.setNombreColis(colis.getNumberOfElements());
        pColis.setTotalColis((int) colis.getTotalElements());
        pColis.setTotalPages(colis.getTotalPages());

        return pColis;
    }

    @Override
    public List<Colis> findAllSendColis(String nomSite, int enable) {
        return colisRepository.findAllSendColis(nomSite, enable);
    }

    @Override
    public List<Colis> findSendColisByReference(String referenceColis, String nomSite, int enable) {
        return colisRepository.findSendColisByReference(referenceColis, nomSite, enable);
    }

    @Override
    public Colis findColisByReference(String ref) {
        return colisRepository.findColisByReference(ref);
    }

    @Override
    public Colis updateColis(Colis c) {

        Colis colis = colisRepository.findColisByReference(c.getReference());

        Collection<DetailsColis> detailsColis = new ArrayList<>();

        if (colis == null) throw new RuntimeException("Ce colis n'existe pas");
        colis.setNomDestinataire(c.getNomDestinataire());
        colis.setContactDestinataire(c.getContactDestinataire());
        colis.setDescription(c.getDescription());
        colis.setValeurColis(c.getValeurColis());
        colis.setQrCode(c.getQrCode());
        colis.setAdresseDestinataire(c.getAdresseDestinataire());
        colis.setEnregistrementColis(c.getEnregistrementColis());
        colis.setExpeditionColis(c.getExpeditionColis());
        colis.setArriveeColis(c.getArriveeColis());
        colis.setReceptionColis(c.getReceptionColis());
        colis.setLivraisonColis(c.getLivraisonColis());

        removeDetailsColis(colis);

        addDetailsColis(c.getDetailsColis(), colis);

        colis.setClient(c.getClient());
        colis.setEnable(c.getEnable());
        colis.setUpdateAt(new Date());

        return colisRepository.save(colis);
    }

    @Override
    public double updateDetailsColis(Collection<DetailsColis> dColis, Colis colis) {
        List<DetailsColis> detailsColis = new ArrayList<>();
        double[] montant = new double[1];
        montant[0] = 0;
        System.out.println("+++++++++++++++++++++++++ " + dColis.size() + " +++++++++++++++++++++++++");
        if (dColis.size() > 0) {
            dColis.forEach((dc) -> {
                System.out.println("+++++++++++++++++++++++++ " + dc.getDesignation() + " +++++++++++++++++++++++++");
                dc.setColis(colis);
                DetailsColis deColis;
                System.out.println("+++++++++++++++++++++++++ " + dc.getPrixUnitaire()  + " +++++++++++++++++++++++++");
                dc.setPrixTotal(dc.getPrixUnitaire()*dc.getPoids());
                deColis = detailsColisService.updateDetailsColis(dc);
                System.out.println("+++++++++++++++++++++++++ " + deColis.getPrixUnitaire()  + " +++++++++++++++++++++++++");
                detailsColis.add(deColis);
                montant[0] += dc.getPrixTotal();
            });
            colis.setDetailsColis(detailsColis);
        }
        return montant[0];
    }

    @Override
    public void addDetailsColis(Collection<DetailsColis> dColis, Colis colis) {
        List<DetailsColis> detailsColis = new ArrayList<>();
        if (dColis.size() > 0) {
            dColis.forEach((dc) -> {
                dc.setColis(colis);
                DetailsColis deColis;
                dc.setPrixTotal(dc.getPrixUnitaire()*dc.getPoids());
                deColis = detailsColisService.saveDetailsColis(dc);
                detailsColis.add(deColis);
            });
            colis.setDetailsColis(detailsColis);
        }
    }

    @Override
    public void removeDetailsColis(Colis colis) {
        if (colis.getDetailsColis().size() > 0) {
            colis.getDetailsColis().forEach((dc) -> {
                detailsColisService.removeDetailsColisById(dc.getId());
            });
        }
    }

    @Override
    public void disableColis(String ref, Date date) {
        Colis colis = colisRepository.findColisByReference(ref);
        if (colis == null) throw new RuntimeException("Ce colis n'existe pas");
        colisRepository.disableColis(ref, new Date());
    }

    @Override
    public void enableColis(String ref, Date date) {
        Colis colis = colisRepository.findColisByReference(ref);
        if (colis == null) throw new RuntimeException("Ce colis n'existe pas");
        colisRepository.enableColis(ref, new Date());
    }

    @Override
    public void removeColisByReference(String ref) {
        Colis colis = colisRepository.findColisByReference(ref);
        if (colis == null) throw new RuntimeException("Ce colis n'existe pas");
        colisRepository.removeColisByReference(ref);

        if (colis.getDetailsColis().size() > 0) {
            colis.getDetailsColis().forEach((dc) -> {
                detailsColisService.removeDetailsColisById(dc.getId());
            });
        }
    }

    //------------------------- OTHER ---------------------------------------

    @Override
    public Colis addEnregistrementColis(Colis colis) {
        Colis c = colisRepository.findColisByReference(colis.getReference());
        if (c == null) throw new RuntimeException("Ce colis n'existe pas");
        EnregistrementColis enregistrementColis = new EnregistrementColis();
        enregistrementColis.setDescription(colis.getEnregistrementColis().getDescription());
        enregistrementColis.setDateEnregistrement(new Date());
        enregistrementColis.setUtilisateur(colis.getUtilisateur());
        EnregistrementColis eColis = enregistrementColisRepository.save(enregistrementColis);
        colis.setEnregistrementColis(eColis);
        return colis;
    }

    @Override
    public Colis updateEnregistrementColis(Colis colis) {
        EnregistrementColis enregistrementColis = enregistrementColisRepository.getEnregistrementColisById(colis.getEnregistrementColis().getId());
        if (enregistrementColis == null) throw new RuntimeException("Ce colis n'a pas encore été enregistré");
        enregistrementColis.setDescription(colis.getEnregistrementColis().getDescription());
        EnregistrementColis eColis = enregistrementColisRepository.save(enregistrementColis);
        colis.setEnregistrementColis(eColis);
        return colis;
    }

    @Override
    public Colis addExpeditionColis(Colis colis, Utilisateur utilisateur) {
        Colis c = colisRepository.findColisByReference(colis.getReference());
        if (c == null) throw new RuntimeException("Ce colis n'existe pas");
        ExpeditionColis expeditionColis = new ExpeditionColis();
        expeditionColis.setDescription(colis.getExpeditionColis().getDescription());
        expeditionColis.setDateExpedition(new Date());
        expeditionColis.setUtilisateur(utilisateur);
        ExpeditionColis eColis = expeditionColisRepository.save(expeditionColis);
        c.setExpeditionColis(eColis);
        return c;
    }

    @Override
    public Colis updateExpeditionColis(Colis colis) {
        ExpeditionColis expeditionColis = expeditionColisRepository.getExpeditionColisById(colis.getExpeditionColis().getId());
        if (expeditionColis == null) throw new RuntimeException("Ce colis n'a pas encore été expedié");
        expeditionColis.setDescription(colis.getExpeditionColis().getDescription());
        ExpeditionColis eColis = expeditionColisRepository.save(expeditionColis);
        colis.setExpeditionColis(eColis);
        return colis;
    }

    @Override
    public Colis addArriveeColis(Colis colis, Utilisateur utilisateur) {
        Colis c = colisRepository.findColisByReference(colis.getReference());
        if (c == null) throw new RuntimeException("Ce colis n'existe pas");
        ArriveeColis arriveeColis = new ArriveeColis();
        arriveeColis.setDescription(colis.getArriveeColis().getDescription());
        arriveeColis.setDateArrivee(new Date());
        arriveeColis.setUtilisateur(utilisateur);
        ArriveeColis aColis = arriveeColisRepository.save(arriveeColis);
        c.setArriveeColis(aColis);
        return c;
    }

    @Override
    public Colis updateArriveeColis(Colis colis) {
        ArriveeColis arriveeColis = arriveeColisRepository.getArriveeColisById(colis.getArriveeColis().getId());
        if (arriveeColis == null) throw new RuntimeException("Ce colis n'est pas encore arrivé");
        arriveeColis.setDescription(colis.getArriveeColis().getDescription());
        ArriveeColis aColis = arriveeColisRepository.save(arriveeColis);
        colis.setArriveeColis(aColis);
        return colis;
    }

    @Override
    public Colis addReceptionColis(Colis colis, Utilisateur utilisateur) {
        Colis c = colisRepository.findColisByReference(colis.getReference());
        if (c == null) throw new RuntimeException("Ce colis n'existe pas");
        ReceptionColis receptionColis = new ReceptionColis();
        receptionColis.setDescription(colis.getReceptionColis().getDescription());
        receptionColis.setDateReception(new Date());
        receptionColis.setUtilisateur(utilisateur);
        ReceptionColis rColis = receptionColisRepository.save(receptionColis);
        c.setReceptionColis(rColis);
        return colis;
    }

    @Override
    public Colis updateReceptionColis(Colis colis) {
        ReceptionColis receptionColis = receptionColisRepository.getReceptionColisById(colis.getReceptionColis().getId());
        if (receptionColis == null) throw new RuntimeException("Ce colis n'a pas été réceptionné");
        receptionColis.setDescription(colis.getReceptionColis().getDescription());
        ReceptionColis rColis = receptionColisRepository.save(receptionColis);
        colis.setReceptionColis(rColis);
        return colis;
    }

    @Override
    public Colis addLivraisonColis(Colis colis, Utilisateur utilisateur) {
        Colis c = colisRepository.findColisByReference(colis.getReference());
        if (c == null) throw new RuntimeException("Ce colis n'existe pas");
        LivraisonColis livraisonColis = new LivraisonColis();
        livraisonColis.setDescription(colis.getLivraisonColis().getDescription());
        livraisonColis.setDateLivraison(new Date());
        livraisonColis.setUtilisateur(utilisateur);
        LivraisonColis lColis = livraisonColisRepository.save(livraisonColis);
        c.setLivraisonColis(lColis);
        return c;
    }

    @Override
    public Colis updateLivraisonColis(Colis colis) {
        LivraisonColis livraisonColis = livraisonColisRepository.getLivraisonColisById(colis.getLivraisonColis().getId());
        if (livraisonColis == null) throw new RuntimeException("Ce colis n'a pas été livré");
        livraisonColis.setDescription(colis.getLivraisonColis().getDescription());
        LivraisonColis lColis = livraisonColisRepository.save(livraisonColis);
        colis.setLivraisonColis(lColis);
        return colis;
    }

    @Override
    public String countSendColis(Long idSite, int enable) {
        int value = colisRepository.countSendColis(idSite, enable);
        String countString = String.valueOf(value);
        Count count = new Count();
        countString = count.count(countString, value);
        return countString;
    }

    @Override
    public String countReceiveColis(Long idSite, int enable) {
        int value = colisRepository.countReceiveColis(idSite, enable);
        String countString = String.valueOf(value);
        Count count = new Count();
        countString = count.count(countString, value);
        return countString;
    }

    @Override
    public String countClientColis(Long idClient, int enable) {
        int value = colisRepository.countClientColis(idClient, enable);
        String countString = String.valueOf(value);
        Count count = new Count();
        countString = count.count(countString, value);
        return countString;
    }

    @Override
    public JasperPrint exportQrCodePdf(String referenceColis) throws SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = null;
        try {
            InputStream jasperStream = this.getClass().getResourceAsStream("/report/qrcode.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport jasperReport = compileReport(design);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("reference_colis", referenceColis);

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

        } catch (JRException e) {
            log.info("Error loading file jrxml");
        }

        conn.close();

        return jasperPrint;
    }

}
