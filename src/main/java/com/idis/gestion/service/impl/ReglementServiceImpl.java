package com.idis.gestion.service.impl;

import com.idis.gestion.dao.MouvementRepository;
import com.idis.gestion.dao.ReglementRepository;
import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.Reglement;
import com.idis.gestion.service.ReglementService;
import com.idis.gestion.service.pagination.PageReglement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReglementServiceImpl implements ReglementService {

    private final ReglementRepository reglementRepository;

    private final MouvementRepository mouvementRepository;

    public ReglementServiceImpl(ReglementRepository reglementRepository, MouvementRepository mouvementRepository) {
        this.reglementRepository = reglementRepository;
        this.mouvementRepository = mouvementRepository;
    }

    @Override
    public Reglement saveReglement(Reglement r) {

        r.setCreateAt(new Date());
        r.setUpdateAt(new Date());
        r.setCredit(r.getMontantRegle());

        updateMontantFactureRegle(r, r, false);

        return reglementRepository.save(r);
    }

    @Override
    public Reglement updateReglement(Reglement r) {
        Reglement reglement = reglementRepository.getReglementById(r.getId());
        if(reglement == null) throw new RuntimeException("Ce règlement n'existe pas");

        updateMontantFactureRegle(r, reglement, true);

        reglement.setFacture(r.getFacture());
        reglement.setMontantRegle(r.getMontantRegle());
        reglement.setTypeReglement(r.getTypeReglement());
        reglement.setCreateAt(r.getCreateAt());
        reglement.setUpdateAt(new Date());

        return reglementRepository.save(reglement);
    }

    private void updateMontantFactureRegle(Reglement r, Reglement reglement, boolean update){

        double[] montant = new double[1];
        montant[0] = 0;

        Facture f = mouvementRepository.getFactureByNumeroFacture(reglement.getFacture().getNumeroFacture());
        f.getReglements().forEach(rg ->{
            montant[0] += rg.getMontantRegle();
        });

        if(update){
            if(f.getDebit() < montant[0] + r.getMontantRegle() - reglement.getMontantRegle()){
                throw new RuntimeException("Le montant restant à régler est : " + (f.getDebit() - montant[0] + reglement.getMontantRegle()) + " " + f.getDevise().getNomDevise() );
            }
            montant[0] = montant[0] + r.getMontantRegle() - reglement.getMontantRegle();
        }else{
            if(f.getDebit() < montant[0] + r.getMontantRegle()){
                throw new RuntimeException("Le montant restant à régler est : " + (f.getDebit() - montant[0]) + " " + f.getDevise().getNomDevise() );
            }
            montant[0] = montant[0] + r.getMontantRegle();
        }

        f.setMontantFactureRegle(montant[0]);
        mouvementRepository.save(f);
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
        Facture f = reglement.getFacture();
        f.setMontantFactureRegle(f.getMontantFactureRegle() - reglement.getMontantRegle());
        reglementRepository.removeReglementById(id);
    }
}
