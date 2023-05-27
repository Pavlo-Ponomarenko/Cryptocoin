package org.blockchain.converters;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateConverterImpl implements DateConverter {

    private final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime fromDTOtoEntity(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(s, formatter);
    }

    @Override
    public String fromEntityToDTO(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return date.format(formatter);
    }
}
