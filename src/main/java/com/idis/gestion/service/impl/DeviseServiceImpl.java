package com.idis.gestion.service.impl;

import com.idis.gestion.dao.DeviseRepository;
import com.idis.gestion.entities.Devise;
import com.idis.gestion.service.DeviseService;
import com.idis.gestion.service.pagination.PageDevise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DeviseServiceImpl implements DeviseService {

    @Autowired
    private DeviseRepository deviseRepository;

    @Override
    public PageDevise listDevises(String nomDevise, int enable, Pageable pageable) {
        Page<Devise> devise = deviseRepository.findAllByNomDevise(nomDevise, enable, pageable);
        PageDevise pDevise = new PageDevise();
        pDevise.setDevises(devise.getContent());
        pDevise.setPage(devise.getNumber());
        pDevise.setNombreDevises(devise.getNumberOfElements());
        pDevise.setTotalDevises((int)devise.getTotalElements());
        pDevise.setTotalPages(devise.getTotalPages());
        return pDevise;
    }

    @Override
    public List<Devise> findAllDevises(int enable) {
        return deviseRepository.findAll(enable);
    }

    @Override
    public Devise saveDevise(Devise p) {
        p.setCreateAt(new Date());
        p.setUpdateAt(new Date());
        return deviseRepository.save(p);
    }

    @Override
    public Devise getDeviseById(Long id) {
        return deviseRepository.getDeviseById(id);
    }

    @Override
    public Devise updateDevise(Devise p) {
        Devise devise = deviseRepository.getDeviseById(p.getId());
        if(devise == null) throw new RuntimeException("Cette devise n'existe pas");
        devise.setNomDevise(p.getNomDevise());
        devise.setDescription(p.getDescription());
        devise.setEnable(p.getEnable());
        devise.setUpdateAt(new Date());
        return deviseRepository.save(devise);
    }

    @Override
    public void enableDevise(Long id, Date date) {
        Devise devise = deviseRepository.getDeviseById(id);
        if(devise == null) throw new RuntimeException("Cette devise n'existe pas");
        deviseRepository.enableDevise(id, date);
    }

    @Override
    public void disableDevise(Long id, Date date) {
        Devise devise = deviseRepository.getDeviseById(id);
        if(devise == null) throw new RuntimeException("Cette devise n'existe pas");
        deviseRepository.disableDevise(id, date);
    }

    @Override
    public void removeDeviseById(Long id) {
        Devise devise = deviseRepository.getDeviseById(id);
        if(devise == null) throw new RuntimeException("Cette devise n'existe pas");
        deviseRepository.removeDeviseById(id);
    }
}
