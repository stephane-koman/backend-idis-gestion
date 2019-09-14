package com.idis.gestion.dao;

import com.idis.gestion.entities.EnregistrementColis;
import com.idis.gestion.entities.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface EnregistrementColisRepository extends PagingAndSortingRepository<EnregistrementColis,Long> {

    public EnregistrementColis getEnregistrementColisById(long id);

}
