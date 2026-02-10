package com.quickticket.quickticket.shared.utils;

import com.quickticket.quickticket.domain.seat.domain.Seat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class BaseCustomRepository<T, ID> {
    @PersistenceContext
    private EntityManager em;

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCustomRepository() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) type.getActualTypeArguments()[0];
    }

    protected Optional<T> getEntityById(ID id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }
}
