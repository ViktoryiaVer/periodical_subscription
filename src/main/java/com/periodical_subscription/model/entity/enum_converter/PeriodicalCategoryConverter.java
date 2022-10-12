package com.periodical_subscription.model.entity.enum_converter;

import com.periodical_subscription.model.entity.Periodical;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PeriodicalCategoryConverter implements AttributeConverter<Periodical.Category, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Periodical.Category category) {
        if (category == null) {
            return null;
        }
        return category.getId();
    }

    @Override
    public Periodical.Category convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Periodical.Category.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}