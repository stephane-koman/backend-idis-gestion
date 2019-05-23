package com.idis.gestion.service.impl;

import com.idis.gestion.dao.DetailsColisRepository;
import com.idis.gestion.dao.LigneFactureRepository;
import com.idis.gestion.entities.DetailsColis;
import com.idis.gestion.entities.LigneFacture;
import com.idis.gestion.service.DetailsColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DetailsColisImpl implements DetailsColisService {

    @Autowired
    private DetailsColisRepository detailsColisRepository;

    @Autowired
    private LigneFactureRepository ligneFactureRepository;

    @Override
    public List<DetailsColis> listDetailsColis(int enable) {
        return detailsColisRepository.findAllDetailsColis(enable);
    }

    @Override
    public DetailsColis saveDetailsColis(DetailsColis d) {
        d.setCreateAt(new Date());
        d.setUpdateAt(new Date());
        d.setEnable(1);
        return detailsColisRepository.save(d);
    }

    @Override
    public DetailsColis getDetailsColisById(Long id) {
        return detailsColisRepository.getDetailsColisById(id);
    }

    @Override
    public DetailsColis updateDetailsColis(DetailsColis d) {
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(d.getId());
        if(detailsColis == null) throw new RuntimeException("Aucun détails colis trouvé");
        detailsColis.setDescription(d.getDescription());
        detailsColis.setDesignation(d.getDesignation());
        detailsColis.setColis(d.getColis());
        detailsColis.setPoids(d.getPoids());
        detailsColis.setQuantite(d.getQuantite());
        detailsColis.setUpdateAt(new Date());
        return detailsColisRepository.save(detailsColis);
    }

    @Override
    public void enableDetailsColis(Long id, Date date) {
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(id);
        if(detailsColis == null) throw new RuntimeException("Aucun détails colis trouvé");
        detailsColisRepository.enableDetailsColis(id, date);
    }

    @Override
    public void disableDetailsColis(Long id, Date date) {
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(id);
        if(detailsColis == null) throw new RuntimeException("Aucun détails colis trouvé");
        detailsColisRepository.disableDetailsColis(id, date);
    }

    @Override
    public void removeDetailsColisById(Long id) {
        DetailsColis detailsColis = detailsColisRepository.getDetailsColisById(id);
        if(detailsColis == null) throw new RuntimeException("Aucun détails colis trouvé");
        detailsColisRepository.removeDetailsColisById(id);
    }
}
