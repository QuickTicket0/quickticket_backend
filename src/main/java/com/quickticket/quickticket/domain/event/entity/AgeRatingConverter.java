package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AgeRatingConverter extends AbstractOrdinalEnumConverter<AgeRating> {
    public AgeRatingConverter() { super(AgeRating.class); }
}
