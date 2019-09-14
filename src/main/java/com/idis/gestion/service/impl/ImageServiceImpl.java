package com.idis.gestion.service.impl;

import com.idis.gestion.dao.ImageRepository;
import com.idis.gestion.entities.Colis;
import com.idis.gestion.entities.Image;
import com.idis.gestion.exception.FileStorageException;
import com.idis.gestion.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ServletContext context;

    @Override
    public Image saveImage(MultipartFile file, Colis colis){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Image image = new Image();

            /*boolean isExist = new File(context.getRealPath( "/images/" )).exists();

            if(!isExist){
                new File(context.getRealPath( "/images/" )).mkdir();
            }*/

            String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);

            /*File serverFile = new File(context.getRealPath("/images/" + File.separator + modifiedFileName));

            try{
                FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }*/

            image.setNomImage(modifiedFileName);
            image.setFile(file.getBytes());
            image.setColis(colis);
            image.setCreateAt(new Date());
            image.setUpdateAt(new Date());

            return imageRepository.save(image);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

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
        Image image = imageRepository.getImageById(id);
        if(image == null) throw new RuntimeException("Aucune image trouvée");
        imageRepository.removeImageById(id);
    }
}
