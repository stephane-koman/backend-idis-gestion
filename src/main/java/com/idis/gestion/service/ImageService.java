package com.idis.gestion.service;

import com.idis.gestion.entities.Colis;
import com.idis.gestion.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface ImageService {
    public Image saveImage(MultipartFile file, Colis colis);
    public Image getImageById(Long id);
    public void disableImage(Long id, Date date);
    public void enableImage(Long id, Date date);
    public void removeImageById(Long id);
}
