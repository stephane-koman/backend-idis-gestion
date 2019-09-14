package com.idis.gestion.dao;

import com.idis.gestion.entities.LivraisonColis;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LivraisonColisRepository extends PagingAndSortingRepository<LivraisonColis,Long> {

    public LivraisonColis getLivraisonColisById(long id);

}
