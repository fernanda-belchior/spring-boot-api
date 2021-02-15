package br.com.fernanda.springbootinterview.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil(){}

    private static String defaultFormat = "yyyy-MM-dd";

    /**
     * @param date receives data in format yyyy-MM-dd
     * @return true if "date" is a validate date
     */
    public static boolean isValidDate(String date){
        SimpleDateFormat format = new SimpleDateFormat(defaultFormat);

        try {
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param date receives data in format yyyy-MM-dd
     * @return date in LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultFormat);
        return LocalDate.parse(date, formatter);
    }

    /**
     *
     * @param date receives a date in LocalDate
     * @return data in format yyyy-MM-dd
     */
    public static String convertLocalDateToString(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(defaultFormat));
    }

}

