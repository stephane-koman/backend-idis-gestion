package com.idis.gestion.dao;

import com.idis.gestion.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {

    public Image getImageById(Long id);

    @Modifying
    @Query("update Image i set i.enable = 1, i.updateAt = ?2 where i.id = ?1")
    public void enableImage(Long id, Date date);

    @Modifying
    @Query("update Image i set i.enable = 0, i.updateAt = ?2 where i.id = ?1")
    public void disableImage(Long id, Date date);

    public void removeImageById(Long id);
}
