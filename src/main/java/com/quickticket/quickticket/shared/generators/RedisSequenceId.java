package com.quickticket.quickticket.shared.generators;

import org.hibernate.annotations.IdGeneratorType;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(RedisSequenceIdGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RedisSequenceId {
    String key();
}
