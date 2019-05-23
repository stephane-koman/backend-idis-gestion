package com.idis.gestion.service;

import com.idis.gestion.entities.TypeReglement;
import com.idis.gestion.service.pagination.PageTypeReglement;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TypeReglementService {
    public PageTypeReglement listTypesReglement(String nomTypeReglement, int enable, Pageable pageable);
    public List<TypeReglement> findAllTypesReglement(int enable);
    public TypeReglement saveTypeReglement(TypeReglement tr);
    public TypeReglement getTypeReglementById(Long id);
    public TypeReglement updateTypeReglement(TypeReglement tr);
    public void enableTypeReglement(Long id, Date date);
    public void disableTypeReglement(Long id, Date date);
    public void removeTypeReglementById(Long id);
}
