package com.quickticket.quickticket.shared.converters;

import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public abstract class AbstractOrdinalEnumConverter<T extends Enum<T> & OrdinalEnum> implements AttributeConverter<T, Short> {
    private final Class<T> clazz;

    @Override
    public Short convertToDatabaseColumn(T attribute) {
        return (attribute == null) ? null : ((short)attribute.getCode());
    }

    @Override
    public T convertToEntityAttribute(Short dbData) {
        if (dbData == null) return null;

        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.getCode() == dbData)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
    }
}