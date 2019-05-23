package com.idis.gestion.service.impl;

import com.idis.gestion.dao.FonctionRepository;
import com.idis.gestion.entities.Fonction;
import com.idis.gestion.service.FonctionService;
import com.idis.gestion.service.FonctionService;
import com.idis.gestion.service.pagination.PageFonction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FonctionServiceImpl implements FonctionService {

    @Autowired
    private FonctionRepository fonctionRepository;

    @Override
    public PageFonction listFonctions(String nomFonction, int enable, Pageable pageable) {
        Page<Fonction> fonctions = fonctionRepository.findAllByNomFonction(nomFonction, enable, pageable);
        PageFonction pFonctions = new PageFonction();
        pFonctions.setFonctions(fonctions.getContent());
        pFonctions.setPage(fonctions.getNumber());
        pFonctions.setNombreFonctions(fonctions.getNumberOfElements());
        pFonctions.setTotalFonctions((int)fonctions.getTotalElements());
        pFonctions.setTotalPages(fonctions.getTotalPages());
        return pFonctions;
    }

    @Override
    public List<Fonction> findAllFonctions(int enable) {
        return fonctionRepository.findAll(enable);
    }

    @Override
    public Fonction saveFonction(Fonction p) {
        p.setCreateAt(new Date());
        p.setUpdateAt(new Date());
        return fonctionRepository.save(p);
    }

    @Override
    public Fonction getFonctionById(Long id) {
        return fonctionRepository.getFonctionById(id);
    }

    @Override
    public Fonction updateFonction(Fonction p) {
        Fonction fonction = fonctionRepository.getFonctionById(p.getId());
        if(fonction == null) throw new RuntimeException("Cette fonction n'existe pas");
        fonction.setNomFonction(p.getNomFonction());
        fonction.setDescription(p.getDescription());
        fonction.setEnable(p.getEnable());
        fonction.setUpdateAt(new Date());
        return fonctionRepository.save(fonction);
    }

    @Override
    public void enableFonction(Long id, Date date) {
        Fonction fonction = fonctionRepository.getFonctionById(id);
        if(fonction == null) throw new RuntimeException("Cette fonction n'existe pas");
        fonctionRepository.enableFonction(id, date);
    }

    @Override
    public void disableFonction(Long id, Date date) {
        Fonction fonction = fonctionRepository.getFonctionById(id);
        if(fonction == null) throw new RuntimeException("Cette fonction n'existe pas");
        fonctionRepository.disableFonction(id, date);
    }

    @Override
    public void removeFonctionById(Long id) {
        Fonction fonction = fonctionRepository.getFonctionById(id);
        if(fonction == null) throw new RuntimeException("Cette fonction n'existe pas");
        fonctionRepository.removeFonctionById(id);
    }
}
