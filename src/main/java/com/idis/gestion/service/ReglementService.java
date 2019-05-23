package com.idis.gestion.service;

import com.idis.gestion.entities.Reglement;
import com.idis.gestion.entities.Reglement;
import com.idis.gestion.service.pagination.PageReglement;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ReglementService {
    public Reglement saveReglement(Reglement r);
    public Reglement updateReglement(Reglement r);
    public Reglement getReglementById(Long id);
    public PageReglement listReglements(String nomTypeReglement, String numeroFacture, int enable, Pageable pageable);
    public void disableReglement(Long id, Date date);
    public void enableReglement(Long id, Date date);
    public void removeReglementById(Long id);
}
