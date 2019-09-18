package com.idis.gestion.entities.generator;
import com.idis.gestion.dao.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class CodeClientGenerator {

    PersonneRepository personneRepository;

    public CodeClientGenerator(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public String generate() {
        String generate = "";

        Long nbr = this.personneRepository.countClients( 2 );

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return "CLT-" + generate + "-" + calendar.get(Calendar.YEAR);
    }
}
