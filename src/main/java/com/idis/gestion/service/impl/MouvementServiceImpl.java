package com.idis.gestion.service.impl;

import com.idis.gestion.dao.LigneFactureRepository;
import com.idis.gestion.dao.MouvementRepository;
import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.LigneFacture;
import com.idis.gestion.entities.Mouvement;
import com.idis.gestion.entities.generator.NumeroAvoirGenerator;
import com.idis.gestion.entities.generator.NumeroFactureGenerator;
import com.idis.gestion.service.MouvementService;
import com.idis.gestion.service.pagination.PageFacture;
import com.idis.gestion.service.pagination.PageMouvement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class MouvementServiceImpl implements MouvementService {

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private LigneFactureRepository ligneFactureRepository;


    @Override
    public Facture saveFacture(Facture f) {
        f.setCreateAt(new Date());
        f.setUpdateAt(new Date());

        Facture facture = mouvementRepository.save(f);

        double montant = addLigneFacture(f.getLigneFactures(), facture);

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

        removeLigneFacture(facture);

        double montant = addLigneFacture(f.getLigneFactures(), facture);

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
    public List<Facture> findAllFactures(int enable) {
        return mouvementRepository.findAllFactures(enable);
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
        removeLigneFacture(facture);
        mouvementRepository.removeFactureById(id);
    }

    // --------------------------- PRIVATE FUNCTIONS ---------------------------------------

    private double addLigneFacture(Collection<LigneFacture> lFactures, Facture facture) {
        List<LigneFacture> lignesFacture = new ArrayList<>();
        double[] montant = new double[1];
        montant[0] = 0;
        if (lFactures.size() > 0) {
            lFactures.forEach((lf) -> {
                lf.setFacture(facture);
                LigneFacture ligneFacture;
                lf.setPrixTotal(lf.getPrixUnitaire()*lf.getDetailsColis().getPoids());
                lf.setCreateAt(new Date());
                lf.setUpdateAt(new Date());
                ligneFacture = ligneFactureRepository.save(lf);
                lignesFacture.add(ligneFacture);
                montant[0] += lf.getPrixTotal();
            });
            facture.setLigneFactures(lignesFacture);
        }
        //return (1 + facture.getTva().getValeurTva()) * montant[0];
        return montant[0];
    }

    /*private double updateLigneFacture(Collection<LigneFacture> lFactures, Facture facture) {
        List<LigneFacture> lignesFacture = new ArrayList<>();
        double[] montant = new double[1];
        montant[0] = 0;
        if (lFactures.size() > 0) {
            lFactures.forEach((lf) -> {
                lf.setFacture(facture);
                LigneFacture ligneFacture;
                lf.setPrixTotal(lf.getPrixUnitaire()*lf.getDetailsColis().getPoids());
                lf.setUpdateAt(new Date());
                ligneFacture = ligneFactureRepository.save(lf);
                lignesFacture.add(ligneFacture);
                montant[0] += lf.getPrixTotal();
            });
            facture.setLigneFactures(lignesFacture);
        }
        return (1 + facture.getTva().getValeurTva()) * montant[0];
    }*/

    private void removeLigneFacture(Facture facture) {
        if (facture.getLigneFactures().size() > 0) {
            facture.getLigneFactures().forEach((lf) -> {
                ligneFactureRepository.removeLigneFactureById(lf.getId());
            });
        }
    }
}
