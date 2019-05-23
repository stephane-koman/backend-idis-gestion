package com.idis.gestion.service.impl;

import com.idis.gestion.dao.MouvementRepository;
import com.idis.gestion.dao.ReglementRepository;
import com.idis.gestion.entities.Mouvement;
import com.idis.gestion.entities.Reglement;
import com.idis.gestion.service.ReglementService;
import com.idis.gestion.service.pagination.PageReglement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ReglementServiceImpl implements ReglementService {

    @Autowired
    private ReglementRepository reglementRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Override
    public Reglement saveReglement(Reglement r) {

        r.setCreateAt(new Date());
        r.setUpdateAt(new Date());
        r.setCredit(r.getMontantRegle());

        return reglementRepository.save(r);
    }

    @Override
    public Reglement updateReglement(Reglement r) {
        Reglement reglement = reglementRepository.getReglementById(r.getId());
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglement.setFacture(r.getFacture());
        reglement.setMontantRegle(r.getMontantRegle());
        reglement.setCredit(r.getMontantRegle());
        reglement.setTypeReglement(r.getTypeReglement());
        reglement.setColis(r.getFacture().getColis());
        reglement.setUpdateAt(new Date());
        return reglement;
    }

    @Override
    public Reglement getReglementById(Long id) {
        return reglementRepository.getReglementById(id);
    }

    @Override
    public PageReglement listReglements(String nomTypeReglement, String numeroFacture, int enable, Pageable pageable) {
        Page<Reglement> reglements = reglementRepository.findAllReglements(nomTypeReglement, numeroFacture, enable, pageable);
        PageReglement pageReglement = new PageReglement();
        pageReglement.setReglements(reglements.getContent());
        pageReglement.setPage(reglements.getNumber());
        pageReglement.setNombreReglements(reglements.getNumberOfElements());
        pageReglement.setTotalReglements((int) reglements.getTotalElements());
        pageReglement.setTotalPages(reglements.getTotalPages());
        return pageReglement;
    }

    @Override
    public void disableReglement(Long id, Date date) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglementRepository.disableReglement(id, date);
    }

    @Override
    public void enableReglement(Long id, Date date) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglementRepository.enableReglement(id, date);
    }

    @Override
    public void removeReglementById(Long id) {
        Reglement reglement = reglementRepository.getReglementById(id);
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");
        reglementRepository.removeReglementById(id);
    }
}
