package com.idis.gestion.service.impl;

import com.idis.gestion.dao.ImageRepository;
import com.idis.gestion.entities.Image;
import com.idis.gestion.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image saveImage(Image i) {
        i.setCreateAt(new Date());
        i.setUpdateAt(new Date());
        return imageRepository.save(i);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.getImageById(id);
    }

    @Override
    public void disableImage(Long id, Date date) {
        imageRepository.disableImage(id, date);
    }

    @Override
    public void enableImage(Long id, Date date) {
        imageRepository.enableImage(id, date);
    }

    @Override
    public void removeImageById(Long id) {
        imageRepository.removeImageById(id);
    }
}
