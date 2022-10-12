package com.periodical_subscription.model.entity.enum_converter;

import com.periodical_subscription.model.entity.Subscription;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SubscriptionStatusConverter implements AttributeConverter<Subscription.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Subscription.Status status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public Subscription.Status convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Subscription.Status.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}