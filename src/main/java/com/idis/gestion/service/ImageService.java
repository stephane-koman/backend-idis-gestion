package com.idis.gestion.service;

import com.idis.gestion.entities.Image;

import java.util.Date;

public interface ImageService {
    public Image saveImage(Image i);
    public Image getImageById(Long id);
    public void disableImage(Long id, Date date);
    public void enableImage(Long id, Date date);
    public void removeImageById(Long id);
}
