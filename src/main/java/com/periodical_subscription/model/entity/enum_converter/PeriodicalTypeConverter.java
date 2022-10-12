package com.periodical_subscription.model.entity.enum_converter;

import com.periodical_subscription.model.entity.Periodical;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PeriodicalTypeConverter implements AttributeConverter<Periodical.Type, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Periodical.Type type) {
        if (type == null) {
            return null;
        }
        return type.getId();
    }

    @Override
    public Periodical.Type convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Periodical.Type.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}