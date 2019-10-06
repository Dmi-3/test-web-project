package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.model.Specialization;

import java.util.Collection;

public class SpecializationDao implements ObjectDao<Specialization>
{
    @Override
    public Collection<Specialization> getAll()
    {
        return null;
    }

    @Override
    public Specialization getById(Long id)
    {
        return null;
    }

    @Override
    public boolean create(Specialization specialization)
    {
        return false;
    }

    @Override
    public boolean update(Specialization specialization)
    {
        return false;
    }

    @Override
    public boolean delete(Specialization specialization)
    {
        return false;
    }
}
