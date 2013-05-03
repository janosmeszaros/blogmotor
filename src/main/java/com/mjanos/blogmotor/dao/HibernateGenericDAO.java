package com.mjanos.blogmotor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HibernateGenericDAO<T> implements GenericDAO<T> {

    @Autowired
    private SessionFactory factory;
    private Class<T> persistentClass;

    public HibernateGenericDAO() {
    }

    public HibernateGenericDAO(final Class<T> persistentClass) {
        super();
        this.persistentClass = persistentClass;
    }

    @Transactional
    @Override
    public void persist(final T entity) {
        getSession().saveOrUpdate(entity);
    }

    private Session getSession() {
        return factory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public T update(final T entity) {
        return (T) getSession().merge(entity);
    }

    @Transactional
    @Override
    public void delete(final T entity) {
        getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<T> getByCriteria(final Criterion... criterion) {
        final Criteria crit = getSession().createCriteria(persistentClass);
        if (criterion != null) {
            for (final Criterion c : criterion) {
                crit.add(c);
            }
        }
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public T getById(final long id) {
        return (T) getSession().get(persistentClass, id);
    }

}
