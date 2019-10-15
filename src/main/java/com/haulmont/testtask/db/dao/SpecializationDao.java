package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.ConnectionService;
import com.haulmont.testtask.model.Specialization;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpecializationDao implements ObjectDao<Specialization>
{
    private static final Logger LOG = Logger.getLogger(SpecializationDao.class);

    @Override
    public Collection<Specialization> getAll()
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return Collections.emptyList();
        }

        ArrayList<Specialization> prescriptionPriorities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM specializations"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Specialization specialization = parseResultSet(resultSet);
                if (specialization == null)
                {
                    continue;
                }
                prescriptionPriorities.add(specialization);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return prescriptionPriorities.isEmpty() ? Collections.emptyList() : prescriptionPriorities;
    }

    @Override
    public Specialization getById(Long id)
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM specializations WHERE id = %d", id)))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return parseResultSet(resultSet);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    private Specialization parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Specialization(id, name);
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    @Override
    public boolean create(Specialization specialization)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("INSERT INTO specializations(name) values ('%s')", specialization.getName());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateRequest))
        {
            preparedStatement.execute();
            return true;
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }

    @Override
    public boolean update(Specialization specialization)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("UPDATE specializations SET name = '%s' where id = %d",
                specialization.getName(), specialization.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateRequest))
        {
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Specialization specialization)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("DELETE FROM specializations WHERE id = %d", specialization.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateRequest))
        {
            preparedStatement.execute();
            return true;
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }
}
