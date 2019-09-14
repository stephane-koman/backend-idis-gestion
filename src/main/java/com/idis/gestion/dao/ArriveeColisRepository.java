package com.idis.gestion.dao;

import com.idis.gestion.entities.ArriveeColis;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ArriveeColisRepository extends PagingAndSortingRepository<ArriveeColis,Long> {

    public ArriveeColis getArriveeColisById(long id);

}
