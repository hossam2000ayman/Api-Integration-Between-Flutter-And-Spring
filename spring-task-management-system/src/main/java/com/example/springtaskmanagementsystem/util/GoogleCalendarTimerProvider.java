package com.example.springtaskmanagementsystem.util;

import org.springframework.stereotype.Component;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;

@Component
public class GoogleCalendarTimerProvider {

    public EventDateTime convertTo(LocalDateTime dateTime) {
        Date date = Date.from(dateTime.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());

        DateTime startDateTime = new DateTime(date);
        return new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Sao_Paulo");

    }

    public LocalDateTime convertStringToDateTime(String startDateString, int hour, int minute, int second) {
        // Define the expected date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // Parse the String to LocalDate using the defined formatter
        LocalDate localDate = LocalDate.parse(startDateString, formatter);

        // Set the time to the beginning of the day (hh:mm:ss)
        return localDate.atTime(hour, minute, second);
    }

    public Date convertStringToDate(String startDateString, int hour, int minute, int second) {
        String combinedStr = String.format("%s %02d:%02d:%02d", startDateString, hour, minute, second);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        // Parse the combined string into a Date object
        Date formattedDate = null;
        try {
            formattedDate = dateFormat.parse(combinedStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formattedDate);
        return formattedDate;
    }


}
