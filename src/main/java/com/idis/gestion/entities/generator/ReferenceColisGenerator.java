package com.idis.gestion.entities.generator;
import com.idis.gestion.dao.ColisRepository;

import java.util.Calendar;

public class ReferenceColisGenerator{

    ColisRepository colisRepository;

    public ReferenceColisGenerator(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }


    public String generate(Long codeSite) {
        String generate = "";

        Long nbr = this.colisRepository.countSendColis( codeSite, 2 );

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return "IGC"  + calendar.get(Calendar.YEAR) + codeSite + generate;
    }
}
