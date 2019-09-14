package com.idis.gestion.dao;

import com.idis.gestion.entities.ReceptionColis;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReceptionColisRepository extends PagingAndSortingRepository<ReceptionColis,Long> {

    public ReceptionColis getReceptionColisById(long id);

}
