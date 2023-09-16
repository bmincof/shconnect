package com.shinhan.connector.config;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtils {
    public static LocalDateTime UNIX_TO_DATE(Long unixTime) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime;
    }

    public static Long DATE_TO_UNIX(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        return instant.getEpochSecond();
    }
}
