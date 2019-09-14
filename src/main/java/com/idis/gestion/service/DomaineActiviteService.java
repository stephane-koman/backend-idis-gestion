package com.idis.gestion.service;

import com.idis.gestion.entities.DomaineActivite;
import com.idis.gestion.service.pagination.PageDomaineActivite;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface DomaineActiviteService {
    public PageDomaineActivite listDomaineActivite(String code, int enable, Pageable pageable);
    public List<DomaineActivite> findAllDomaineActivite(int enable);
    public List<DomaineActivite> findDomainesActiviteByCode(String code, int enable);
    public DomaineActivite saveDomaineActivite(DomaineActivite p);
    public DomaineActivite getDomaineActiviteById(Long id);
    public DomaineActivite updateDomaineActivite(DomaineActivite p);
    public void enableDomaineActivite(Long id, Date date);
    public void disableDomaineActivite(Long id, Date date);
    public void removeDomaineActiviteById(Long id);
}
