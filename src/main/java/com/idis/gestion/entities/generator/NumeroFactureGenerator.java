package com.idis.gestion.entities.generator;

import com.idis.gestion.dao.MouvementRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Calendar;

public class NumeroFactureGenerator {

    MouvementRepository mouvementRepository;

    public NumeroFactureGenerator(MouvementRepository mouvementRepository) {
        this.mouvementRepository = mouvementRepository;
    }

    public String generate() {

        String generate = "";

        Long nbr = mouvementRepository.countFactures( "Facture", 2 );

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return "F-"  + calendar.get(Calendar.YEAR)  + "-" + generate;
    }
}
