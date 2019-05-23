package com.idis.gestion.service;

import com.idis.gestion.entities.TypeFacture;
import com.idis.gestion.service.pagination.PageTypeFacture;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TypeFactureService {
    public PageTypeFacture listTypesFacture(String nomTypeFacture, int enable, Pageable pageable);
    public List<TypeFacture> findAllTypesFacture(int enable);
    public TypeFacture saveTypeFacture(TypeFacture tr);
    public TypeFacture getTypeFactureById(Long id);
    public TypeFacture updateTypeFacture(TypeFacture tr);
    public void enableTypeFacture(Long id, Date date);
    public void disableTypeFacture(Long id, Date date);
    public void removeTypeFactureById(Long id);
}
