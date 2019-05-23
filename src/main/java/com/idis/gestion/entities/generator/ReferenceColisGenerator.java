package com.idis.gestion.entities.generator;
import java.util.Calendar;

public class ReferenceColisGenerator{

    public String generate(Long id, String codeSite) {
        String generate = "";

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(id);

        return "IGC"  + calendar.get(Calendar.YEAR) + codeSite + generate;
    }
}
