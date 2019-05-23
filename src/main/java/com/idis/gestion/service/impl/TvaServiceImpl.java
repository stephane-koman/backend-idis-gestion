package com.idis.gestion.service.impl;

import com.idis.gestion.dao.TvaRepository;
import com.idis.gestion.entities.Tva;
import com.idis.gestion.service.TvaService;
import com.idis.gestion.service.pagination.PageTva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TvaServiceImpl implements TvaService {

    @Autowired
    private TvaRepository tvaRepository;

    @Override
    public PageTva listTvas(double valeurTva, int enable, Pageable pageable) {
        Page<Tva> tva = tvaRepository.findAllByValeurTva(valeurTva, enable, pageable);
        PageTva pTva = new PageTva();
        pTva.setTvas(tva.getContent());
        pTva.setPage(tva.getNumber());
        pTva.setNombreTvas(tva.getNumberOfElements());
        pTva.setTotalTvas((int)tva.getTotalElements());
        pTva.setTotalPages(tva.getTotalPages());
        return pTva;
    }

    @Override
    public List<Tva> findAllTvas(int enable) {
        return tvaRepository.findAll(enable);
    }

    @Override
    public Tva saveTva(Tva p) {
        p.setCreateAt(new Date());
        p.setUpdateAt(new Date());
        return tvaRepository.save(p);
    }

    @Override
    public Tva getTvaById(Long id) {
        return tvaRepository.getTvaById(id);
    }

    @Override
    public Tva updateTva(Tva p) {
        Tva tva = tvaRepository.getTvaById(p.getId());
        if(tva == null) throw new RuntimeException("Cette tva n'existe pas");
        tva.setValeurTva(p.getValeurTva());
        tva.setDescription(p.getDescription());
        tva.setEnable(p.getEnable());
        tva.setUpdateAt(new Date());
        return tvaRepository.save(tva);
    }

    @Override
    public void enableTva(Long id, Date date) {
        Tva tva = tvaRepository.getTvaById(id);
        if(tva == null) throw new RuntimeException("Cette tva n'existe pas");
        tvaRepository.enableTva(id, date);
    }

    @Override
    public void disableTva(Long id, Date date) {
        Tva tva = tvaRepository.getTvaById(id);
        if(tva == null) throw new RuntimeException("Cette tva n'existe pas");
        tvaRepository.disableTva(id, date);
    }

    @Override
    public void removeTvaById(Long id) {
        Tva tva = tvaRepository.getTvaById(id);
        if(tva == null) throw new RuntimeException("Cette tva n'existe pas");
        tvaRepository.removeTvaById(id);
    }
}
