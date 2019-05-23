package com.idis.gestion.service;

import com.idis.gestion.entities.LigneFacture;

import java.util.Date;
import java.util.List;

public interface LigneFactureService {
    public List<LigneFacture> listLigneFactures(int enable);
    public LigneFacture saveLigneFacture(LigneFacture lf);
    public LigneFacture getLigneFactureById(Long id);
    public LigneFacture updateLigneFacture(LigneFacture lf);
    public void enableLigneFacture(Long id, Date date);
    public void disableLigneFacture(Long id, Date date);
    public void removeLigneFactureById(Long id);
}
