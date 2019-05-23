package com.idis.gestion.service.impl;

import com.idis.gestion.dao.TypeFactureRepository;
import com.idis.gestion.entities.TypeFacture;
import com.idis.gestion.service.TypeFactureService;
import com.idis.gestion.service.pagination.PageTypeFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TypeFactureServiceImpl implements TypeFactureService {

    @Autowired
    private TypeFactureRepository typeFactureRepository;

    @Override
    public PageTypeFacture listTypesFacture(String nomTypeFacture, int enable, Pageable pageable) {
        Page<TypeFacture> typesFacture = typeFactureRepository.listeTypesFacture(nomTypeFacture, enable, pageable);
        PageTypeFacture pTypeFacture = new PageTypeFacture();
        pTypeFacture.setTypesFacture(typesFacture.getContent());
        pTypeFacture.setPage(typesFacture.getNumber());
        pTypeFacture.setNombreTypesFacture(typesFacture.getNumberOfElements());
        pTypeFacture.setTotalTypesFacture((int) typesFacture.getTotalElements());
        pTypeFacture.setTotalPages(typesFacture.getTotalPages());
        return pTypeFacture;
    }

    @Override
    public List<TypeFacture> findAllTypesFacture(int enable) {
        return typeFactureRepository.findAll(enable);
    }

    @Override
    public TypeFacture saveTypeFacture(TypeFacture tr) {
        tr.setCreateAt(new Date());
        tr.setUpdateAt(new Date());
        return typeFactureRepository.save(tr);
    }

    @Override
    public TypeFacture getTypeFactureById(Long id) {
        return typeFactureRepository.getTypeFactureById(id);
    }

    @Override
    public TypeFacture updateTypeFacture(TypeFacture tr) {
        TypeFacture typeFacture = typeFactureRepository.getTypeFactureById(tr.getId());
        if(typeFacture == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeFacture.setNomTypeFacture(tr.getNomTypeFacture());
        typeFacture.setDescription(tr.getDescription());
        typeFacture.setEnable(tr.getEnable());
        typeFacture.setUpdateAt(new Date());
        return typeFactureRepository.save(typeFacture);
    }

    @Override
    public void enableTypeFacture(Long id, Date date) {
        TypeFacture typeFacture = typeFactureRepository.getTypeFactureById(id);
        if(typeFacture == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeFactureRepository.enableTypeFacture(id, date);
    }

    @Override
    public void disableTypeFacture(Long id, Date date) {
        TypeFacture typeFacture = typeFactureRepository.getTypeFactureById(id);
        if(typeFacture == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeFactureRepository.disableTypeFacture(id, date);
    }

    @Override
    public void removeTypeFactureById(Long id) {
        TypeFacture typeFacture = typeFactureRepository.getTypeFactureById(id);
        if(typeFacture == null) throw new RuntimeException("Ce Type de règlement n'existe pas");
        typeFactureRepository.removeTypeFactureById(id);
    }
}
