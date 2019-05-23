package com.idis.gestion.service.impl;

import com.idis.gestion.dao.PaysRepository;
import com.idis.gestion.dao.TypeReglementRepository;
import com.idis.gestion.entities.Pays;
import com.idis.gestion.entities.TypeReglement;
import com.idis.gestion.service.PaysService;
import com.idis.gestion.service.TypeReglementService;
import com.idis.gestion.service.pagination.PagePays;
import com.idis.gestion.service.pagination.PageTypeReglement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TypeReglementServiceImpl implements TypeReglementService {

    @Autowired
    private TypeReglementRepository typeReglementRepository;

    @Override
    public PageTypeReglement listTypesReglement(String nomTypeReglement, int enable, Pageable pageable) {
        Page<TypeReglement> typesReglement = typeReglementRepository.findAllByLibelle(nomTypeReglement, enable, pageable);
        PageTypeReglement pTypeReglement = new PageTypeReglement();
        pTypeReglement.setTypesReglement(typesReglement.getContent());
        pTypeReglement.setPage(typesReglement.getNumber());
        pTypeReglement.setNombreTypesReglement(typesReglement.getNumberOfElements());
        pTypeReglement.setTotalTypesReglement((int) typesReglement.getTotalElements());
        pTypeReglement.setTotalPages(typesReglement.getTotalPages());
        return pTypeReglement;
    }

    @Override
    public List<TypeReglement> findAllTypesReglement(int enable) {
        return typeReglementRepository.findAll(enable);
    }

    @Override
    public TypeReglement saveTypeReglement(TypeReglement tr) {
        tr.setCreateAt(new Date());
        tr.setUpdateAt(new Date());
        return typeReglementRepository.save(tr);
    }

    @Override
    public TypeReglement getTypeReglementById(Long id) {
        return typeReglementRepository.getTypeReglementById(id);
    }

    @Override
    public TypeReglement updateTypeReglement(TypeReglement tr) {
        TypeReglement typeReglement = typeReglementRepository.getTypeReglementById(tr.getId());
        if(typeReglement == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeReglement.setNomTypeReglement(tr.getNomTypeReglement());
        typeReglement.setDescription(tr.getDescription());
        typeReglement.setEnable(tr.getEnable());
        typeReglement.setUpdateAt(new Date());
        return typeReglementRepository.save(typeReglement);
    }

    @Override
    public void enableTypeReglement(Long id, Date date) {
        TypeReglement typeReglement = typeReglementRepository.getTypeReglementById(id);
        if(typeReglement == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeReglementRepository.enableTypeReglement(id, date);
    }

    @Override
    public void disableTypeReglement(Long id, Date date) {
        TypeReglement typeReglement = typeReglementRepository.getTypeReglementById(id);
        if(typeReglement == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeReglementRepository.disableTypeReglement(id, date);
    }

    @Override
    public void removeTypeReglementById(Long id) {
        TypeReglement typeReglement = typeReglementRepository.getTypeReglementById(id);
        if(typeReglement == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeReglementRepository.removeTypeReglementById(id);
    }
}
