package com.labella.lapag.domain.Util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DataUtil {

    public static LocalDate ajustarParaProximoDiaUtil(LocalDate data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();

        if (diaDaSemana == DayOfWeek.SATURDAY) {
            return data.plusDays(2); // Sábado -> Segunda-feira
        } else if (diaDaSemana == DayOfWeek.SUNDAY) {
            return data.plusDays(1); // Domingo -> Segunda-feira
        } else {
            return data; // Dia útil
        }

    }
}