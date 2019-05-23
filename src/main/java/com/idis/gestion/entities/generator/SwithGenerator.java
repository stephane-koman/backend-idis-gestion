package com.idis.gestion.entities.generator;

public class SwithGenerator {

    public String swithVal(Long val){

        String generator = "";


        switch (val.toString().length()) {
            case 1:
                generator = "000" + val.toString();
                break;
            case 2:
                generator = "00" + val.toString();
                break;
            case 3:
                generator = "0" + val.toString();
                break;
            default:
                generator = val.toString();
                break;
        }
        return generator;
    }
}
