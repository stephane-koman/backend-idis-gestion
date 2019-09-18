package com.idis.gestion.entities.generator;
import com.idis.gestion.dao.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class EmpMatrGenerator {

    PersonneRepository personneRepository;

    public EmpMatrGenerator(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public String generate() {
        String generate = "";

        Long nbr = this.personneRepository.countEmployes( 2 );

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(nbr);

        return "EMP-" + generate + "-" + calendar.get(Calendar.YEAR);
    }
}
