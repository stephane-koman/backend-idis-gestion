package com.idis.gestion.entities.generator;
import java.util.Calendar;

public class EmpMatrGenerator {

    public String generate(Long id) {
        String generate = "";

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(id);

        return "EMP-" + generate + "-" + calendar.get(Calendar.YEAR);
    }
}
