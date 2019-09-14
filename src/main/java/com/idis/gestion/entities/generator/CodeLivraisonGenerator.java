package com.idis.gestion.entities.generator;
import java.util.Calendar;

public class CodeLivraisonGenerator {

    public String generate(Long id, String code_site) {
        String generate = "";

        Calendar calendar = Calendar.getInstance();

        SwithGenerator swithGenerator = new SwithGenerator();

        generate = swithGenerator.swithVal(id);

        return code_site + "-" + generate + "-" + calendar.get(Calendar.YEAR);
    }
}
