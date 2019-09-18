package com.idis.gestion.entities.generator;
import com.idis.gestion.dao.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class CodeLivraisonGenerator {

    ColisRepository colisRepository;

    public CodeLivraisonGenerator(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    public String generate(Long code_site) {
        String generate = "";

        Long nbr = this.colisRepository.countSendColis( code_site, 2 );

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return code_site + "-" + generate + "-" + calendar.get(Calendar.YEAR);
    }
}
