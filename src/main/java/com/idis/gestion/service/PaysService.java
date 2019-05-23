package com.idis.gestion.service;

import com.idis.gestion.entities.Pays;
import com.idis.gestion.service.pagination.PagePays;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface PaysService {
    public PagePays listPays(String nomPays, int enable, Pageable pageable);
    public List<Pays> findAllPays(int enable);
    public Pays savePays(Pays p);
    public Pays getPaysById(Long id);
    public Pays updatePays(Pays p);
    public void enablePays(Long id, Date date);
    public void disablePays(Long id, Date date);
    public void removePaysById(Long id);
}
