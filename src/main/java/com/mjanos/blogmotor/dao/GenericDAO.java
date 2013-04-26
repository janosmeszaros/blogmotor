package com.mjanos.blogmotor.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDAO<T> {
    void persist(T entity);

    T update(T entity);

    void delete(T entity);

    List<T> getByCriteria(Criterion... crit);

    T getById(long id);
}
