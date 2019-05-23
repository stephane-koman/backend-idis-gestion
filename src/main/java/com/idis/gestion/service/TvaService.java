package com.idis.gestion.service;

import com.idis.gestion.entities.Tva;
import com.idis.gestion.service.pagination.PageTva;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TvaService {
    public PageTva listTvas(double valeurTva, int enable, Pageable pageable);
    public List<Tva> findAllTvas(int enable);
    public Tva saveTva(Tva t);
    public Tva getTvaById(Long id);
    public Tva updateTva(Tva t);
    public void enableTva(Long id, Date date);
    public void disableTva(Long id, Date date);
    public void removeTvaById(Long id);
}
