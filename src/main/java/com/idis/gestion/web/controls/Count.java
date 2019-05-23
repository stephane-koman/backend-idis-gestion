package com.idis.gestion.web.controls;

public class Count {
    public String count(String value, int count){

        if(value.length() >= 4 && value.length() < 7){
            int num = Math.round(count/1000);
            value = String.valueOf(num) + "k";
        }
        else if(value.length() >= 7){
            int num = Math.round(count/1000000);
            value = String.valueOf(num) + "M";
        }

        return value;
    }
}
