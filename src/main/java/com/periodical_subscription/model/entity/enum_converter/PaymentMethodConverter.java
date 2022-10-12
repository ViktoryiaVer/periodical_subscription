package com.periodical_subscription.model.entity.enum_converter;

import com.periodical_subscription.model.entity.Payment;
import com.periodical_subscription.model.entity.Periodical;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<Payment.PaymentMethod, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Payment.PaymentMethod method) {
        if (method == null) {
            return null;
        }
        return method.getId();
    }

    @Override
    public Payment.PaymentMethod convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Payment.PaymentMethod.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}