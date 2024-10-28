package com.olimpiodev.tec.carteira.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Data {

    public static String getNow() {
        Calendar agora = Calendar.getInstance();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(agora.getTime());
    }
}
