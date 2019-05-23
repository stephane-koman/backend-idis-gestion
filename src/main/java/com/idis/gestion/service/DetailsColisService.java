package com.idis.gestion.service;

import com.idis.gestion.entities.DetailsColis;

import java.util.Date;
import java.util.List;

public interface DetailsColisService {
    public List<DetailsColis> listDetailsColis(int enable);
    public DetailsColis saveDetailsColis(DetailsColis d);
    public DetailsColis getDetailsColisById(Long id);
    public DetailsColis updateDetailsColis(DetailsColis d);
    public void enableDetailsColis(Long id, Date date);
    public void disableDetailsColis(Long id, Date date);
    public void removeDetailsColisById(Long id);
}
