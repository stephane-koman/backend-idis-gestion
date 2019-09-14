package com.idis.gestion.dao;

import com.idis.gestion.entities.ExpeditionColis;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExpeditionColisRepository extends PagingAndSortingRepository<ExpeditionColis,Long> {

    public ExpeditionColis getExpeditionColisById(long id);

}
