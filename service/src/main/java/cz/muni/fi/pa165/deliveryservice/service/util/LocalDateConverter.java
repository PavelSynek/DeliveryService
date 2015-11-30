package cz.muni.fi.pa165.deliveryservice.service.util;

import org.dozer.DozerConverter;

import java.time.LocalDate;

/**
 * Created by Pavel on 27. 11. 2015.
 */
public class LocalDateConverter extends DozerConverter<LocalDate, LocalDate> {

    public LocalDateConverter() {
        super(LocalDate.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(LocalDate source, LocalDate destination) {
        if (source == null) {
            return null;
        }
        return LocalDate.from(source);
    }

    @Override
    public LocalDate convertFrom(LocalDate source, LocalDate destination) {
        if (source == null) {
            return null;
        }
        return LocalDate.from(source);
    }
}