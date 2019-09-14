package com.idis.gestion.service.impl;

import com.idis.gestion.dao.DomaineActiviteRepository;
import com.idis.gestion.entities.DomaineActivite;
import com.idis.gestion.service.DomaineActiviteService;
import com.idis.gestion.service.DomaineActiviteService;
import com.idis.gestion.service.pagination.PageDomaineActivite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DomaineActiviteServiceImpl implements DomaineActiviteService {

    @Autowired
    private DomaineActiviteRepository domaineActiviteRepository;

    @Override
    public PageDomaineActivite listDomaineActivite(String nomDomaineActivite, int enable, Pageable pageable) {
        Page<DomaineActivite> domaineActivite = domaineActiviteRepository.findAllByCode(nomDomaineActivite, enable, pageable);
        PageDomaineActivite pDomaineActivite = new PageDomaineActivite();
        pDomaineActivite.setDomainesActivite(domaineActivite.getContent());
        pDomaineActivite.setPage(domaineActivite.getNumber());
        pDomaineActivite.setNombreDomaineActivite(domaineActivite.getNumberOfElements());
        pDomaineActivite.setTotalDomaineActivite((int)domaineActivite.getTotalElements());
        pDomaineActivite.setTotalPages(domaineActivite.getTotalPages());
        return pDomaineActivite;
    }

    @Override
    public List<DomaineActivite> findAllDomaineActivite(int enable) {
        return domaineActiviteRepository.findAll(enable);
    }

    @Override
    public List<DomaineActivite> findDomainesActiviteByCode(String code, int enable) {
        return domaineActiviteRepository.findDomaineActiviteByCode(code, enable);
    }

    @Override
    public DomaineActivite saveDomaineActivite(DomaineActivite d) {
        d.setCreateAt(new Date());
        d.setUpdateAt(new Date());
        return domaineActiviteRepository.save(d);
    }

    @Override
    public DomaineActivite getDomaineActiviteById(Long id) {
        return domaineActiviteRepository.getDomaineActiviteById(id);
    }

    @Override
    public DomaineActivite updateDomaineActivite(DomaineActivite d) {
        DomaineActivite domaineActivite = domaineActiviteRepository.getDomaineActiviteById(d.getId());
        if(domaineActivite == null) throw new RuntimeException("Ce domaine d\'activité n'existe pas");
        domaineActivite.setCode(d.getCode());
        domaineActivite.setLibelle(d.getLibelle());
        domaineActivite.setEnable(d.getEnable());
        domaineActivite.setUpdateAt(new Date());
        return domaineActiviteRepository.save(domaineActivite);
    }

    @Override
    public void enableDomaineActivite(Long id, Date date) {
        DomaineActivite domaineActivite = domaineActiviteRepository.getDomaineActiviteById(id);
        if(domaineActivite == null) throw new RuntimeException("Ce domaine d\'activité n'existe pas");
        domaineActiviteRepository.enableDomaineActivite(id, date);
    }

    @Override
    public void disableDomaineActivite(Long id, Date date) {
        DomaineActivite domaineActivite = domaineActiviteRepository.getDomaineActiviteById(id);
        if(domaineActivite == null) throw new RuntimeException("Ce domaine d\'activité n'existe pas");
        domaineActiviteRepository.disableDomaineActivite(id, date);
    }

    @Override
    public void removeDomaineActiviteById(Long id) {
        DomaineActivite domaineActivite = domaineActiviteRepository.getDomaineActiviteById(id);
        if(domaineActivite == null) throw new RuntimeException("Ce domaine d\'activité  n'existe pas");
        domaineActiviteRepository.removeDomaineActiviteById(id);
    }
}
