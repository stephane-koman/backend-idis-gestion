package com.idis.gestion.service;

import com.idis.gestion.entities.Devise;
import com.idis.gestion.service.pagination.PageDevise;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface DeviseService {
    public PageDevise listDevises(String nomDevise, int enable, Pageable pageable);
    public List<Devise> findAllDevises(int enable);
    public Devise saveDevise(Devise d);
    public Devise getDeviseById(Long id);
    public Devise updateDevise(Devise d);
    public void enableDevise(Long id, Date date);
    public void disableDevise(Long id, Date date);
    public void removeDeviseById(Long id);
}
