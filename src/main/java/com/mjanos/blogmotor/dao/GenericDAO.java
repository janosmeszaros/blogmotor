package com.mjanos.blogmotor.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

/**
 * Generic DAO for every entity.
 * @author Janos_Gyula_Meszaros
 * @param <T>
 *            type
 */
public interface GenericDAO<T> {
    /**
     * Save the given entity to database.
     * @param entity
     *            entity to save.
     */
    void persist(T entity);

    /**
     * Update the given entity.
     * @param entity
     *            entity
     * @return updated entity.
     */
    T update(T entity);

    /**
     * Delete the given entity.
     * @param entity
     *            entity to delete.
     */
    void delete(T entity);

    /**
     * Returns list of type with the given criterions.
     * @param crit
     *            criterion list.
     * @return list of type.
     */
    List<T> getByCriteria(Criterion... crit);

    /**
     * Get the entity with specified id.
     * @param id
     *            id.
     * @return entity.
     */
    T getById(long id);
}
