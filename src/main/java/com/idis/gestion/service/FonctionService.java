package com.idis.gestion.service;

import com.idis.gestion.entities.Fonction;
import com.idis.gestion.service.pagination.PageFonction;
import com.idis.gestion.service.pagination.PageFonction;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface FonctionService {
    public PageFonction listFonctions(String nomFonction, int enable, Pageable pageable);
    public List<Fonction> findAllFonctions(int enable);
    public Fonction saveFonction(Fonction f);
    public Fonction getFonctionById(Long id);
    public Fonction updateFonction(Fonction f);
    public void enableFonction(Long id, Date date);
    public void disableFonction(Long id, Date date);
    public void removeFonctionById(Long id);
}
