package org.ahmedukamel.mulham.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getDate(String value) {
        return LocalDate.parse(value, formatter);
    }
}