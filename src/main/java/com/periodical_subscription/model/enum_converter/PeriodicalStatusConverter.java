package com.periodical_subscription.model.enum_converter;

import com.periodical_subscription.model.entity.Periodical;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PeriodicalStatusConverter implements AttributeConverter<Periodical.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Periodical.Status status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public Periodical.Status convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Periodical.Status.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}