package com.idis.gestion.entities.generator;

import com.idis.gestion.dao.ReglementRepository;

import java.util.Calendar;

public class NumeroReglementGenerator {

    ReglementRepository reglementRepository;

    public NumeroReglementGenerator(ReglementRepository reglementRepository) {
        this.reglementRepository = reglementRepository;
    }

    public String generate() {

        String generate = "";

        Long nbr = reglementRepository.count();

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return "R-"  + calendar.get(Calendar.YEAR)  + "-" + generate;
    }
}
