package com.idis.gestion.entities.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Calendar;

public class NumeroAvoirGenerator {

    public String generate(Long id) {

        String generate = "";

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(id);

        return "A-"  + calendar.get(Calendar.YEAR)  + "-" + generate;
    }
}
