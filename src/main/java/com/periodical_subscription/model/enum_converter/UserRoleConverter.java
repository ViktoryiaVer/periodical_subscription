package com.periodical_subscription.model.enum_converter;

import com.periodical_subscription.model.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(User.Role role) {
        if (role == null) {
            return null;
        }
        return role.getId();
    }

    @Override
    public User.Role convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(User.Role.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}