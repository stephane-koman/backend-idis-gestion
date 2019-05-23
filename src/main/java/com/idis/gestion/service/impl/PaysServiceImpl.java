package com.idis.gestion.service.impl;

import com.idis.gestion.dao.PaysRepository;
import com.idis.gestion.entities.Pays;
import com.idis.gestion.service.PaysService;
import com.idis.gestion.service.pagination.PagePays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaysServiceImpl implements PaysService {

    @Autowired
    private PaysRepository paysRepository;

    @Override
    public PagePays listPays(String nomPays, int enable, Pageable pageable) {
        Page<Pays> pays = paysRepository.findAllByNomPays(nomPays, enable, pageable);
        PagePays pPays = new PagePays();
        pPays.setPays(pays.getContent());
        pPays.setPage(pays.getNumber());
        pPays.setNombrePays(pays.getNumberOfElements());
        pPays.setTotalPays((int)pays.getTotalElements());
        pPays.setTotalPages(pays.getTotalPages());
        return pPays;
    }

    @Override
    public List<Pays> findAllPays(int enable) {
        return paysRepository.findAll(enable);
    }

    @Override
    public Pays savePays(Pays p) {
        p.setCreateAt(new Date());
        p.setUpdateAt(new Date());
        return paysRepository.save(p);
    }

    @Override
    public Pays getPaysById(Long id) {
        return paysRepository.getPaysById(id);
    }

    @Override
    public Pays updatePays(Pays p) {
        Pays pays = paysRepository.getPaysById(p.getId());
        if(pays == null) throw new RuntimeException("Ce pays n'existe pas");
        pays.setNomPays(p.getNomPays());
        pays.setDescription(p.getDescription());
        pays.setEnable(p.getEnable());
        pays.setUpdateAt(new Date());
        return paysRepository.save(pays);
    }

    @Override
    public void enablePays(Long id, Date date) {
        Pays pays = paysRepository.getPaysById(id);
        if(pays == null) throw new RuntimeException("Ce pays n'existe pas");
        paysRepository.enablePays(id, date);
    }

    @Override
    public void disablePays(Long id, Date date) {
        Pays pays = paysRepository.getPaysById(id);
        if(pays == null) throw new RuntimeException("Ce pays n'existe pas");
        paysRepository.disablePays(id, date);
    }

    @Override
    public void removePaysById(Long id) {
        Pays pays = paysRepository.getPaysById(id);
        if(pays == null) throw new RuntimeException("Ce pays n'existe pas");
        paysRepository.removePaysById(id);
    }
}
