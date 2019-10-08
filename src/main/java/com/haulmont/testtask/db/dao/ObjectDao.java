package com.haulmont.testtask.db.dao;

import java.util.Collection;

public interface ObjectDao<T>
{
    Collection<T> getAll();

    T getById(Long id);

    boolean create(T object);

    boolean update(T object);

    boolean delete(T object);
}
