package util;

import java.time.LocalDate;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;

public class ConverterUtils {

    public static Date localDateParaDate(LocalDate localDate) {
        java.sql.Date sqlDate = Date.valueOf(localDate);
        return sqlDate;
    }
    
    public static LocalDate dateParaLocalDate(Date date){
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
