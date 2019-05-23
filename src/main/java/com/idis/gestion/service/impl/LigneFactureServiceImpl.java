package com.idis.gestion.service.impl;

import com.idis.gestion.dao.DetailsColisRepository;
import com.idis.gestion.dao.LigneFactureRepository;
import com.idis.gestion.entities.DetailsColis;
import com.idis.gestion.entities.LigneFacture;
import com.idis.gestion.service.LigneFactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LigneFactureServiceImpl implements LigneFactureService {

    @Autowired
    private LigneFactureRepository ligneFactureRepository;

    @Autowired
    private DetailsColisRepository detailsColisRepository;

    @Override
    public List<LigneFacture> listLigneFactures(int enable) {
        return ligneFactureRepository.findAllLigneFactures(enable);
    }

    @Override
    public LigneFacture saveLigneFacture(LigneFacture lf) {
        lf.setCreateAt(new Date());
        lf.setUpdateAt(new Date());
        return ligneFactureRepository.save(lf);
    }

    @Override
    public LigneFacture getLigneFactureById(Long id) {
        return ligneFactureRepository.getLigneFactureById(id);
    }

    @Override
    public LigneFacture updateLigneFacture(LigneFacture lf) {
        LigneFacture ligneFacture = ligneFactureRepository.getLigneFactureById(lf.getId());
        if(ligneFacture == null) throw new RuntimeException("Aucune ligne trouvée");
        ligneFacture.setPrixUnitaire(lf.getPrixUnitaire());
        ligneFacture.setUpdateAt(new Date());
        return ligneFactureRepository.save(ligneFacture);
    }

    @Override
    public void enableLigneFacture(Long id, Date date) {
        LigneFacture ligneFacture = ligneFactureRepository.getLigneFactureById(id);
        detailsColisRepository.enableDetailsColis(ligneFacture.getDetailsColis().getId(), date);
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(ligneFacture.getDetailsColis().getId());
        if(detailsColis != null) throw new RuntimeException("Erreur au cours de l'activation du Détails Colis");
        ligneFactureRepository.enableLigneFacture(id, date);
    }

    @Override
    public void disableLigneFacture(Long id, Date date) {
        LigneFacture ligneFacture = ligneFactureRepository.getLigneFactureById(id);
        detailsColisRepository.disableDetailsColis(ligneFacture.getDetailsColis().getId(), date);
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(ligneFacture.getDetailsColis().getId());
        if(detailsColis != null) throw new RuntimeException("Erreur au cours de la modification du Détails Colis");
        ligneFactureRepository.disableLigneFacture(id, date);
    }

    @Override
    public void removeLigneFactureById(Long id) {
        LigneFacture ligneFacture = ligneFactureRepository.getLigneFactureById(id);
        detailsColisRepository.removeDetailsColisById(ligneFacture.getDetailsColis().getId());
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(ligneFacture.getDetailsColis().getId());
        if(detailsColis != null) throw new RuntimeException("Erreur au cours de la suppression du Détails Colis");
        ligneFactureRepository.removeLigneFactureById(id);
    }
}
