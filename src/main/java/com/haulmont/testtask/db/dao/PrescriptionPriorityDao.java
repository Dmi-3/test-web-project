package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.ConnectionService;
import com.haulmont.testtask.model.PrescriptionPriority;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PrescriptionPriorityDao implements ObjectDao<PrescriptionPriority>
{
    private static final Logger LOG = Logger.getLogger(PrescriptionPriorityDao.class);

    @Override
    public Collection<PrescriptionPriority> getAll()
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return Collections.emptyList();
        }

        ArrayList<PrescriptionPriority> prescriptionPriorities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM prescription_priorities"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                PrescriptionPriority prescriptionPriority = parseResultSet(resultSet);
                if (prescriptionPriority == null)
                {
                    continue;
                }
                prescriptionPriorities.add(prescriptionPriority);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return prescriptionPriorities.isEmpty() ? Collections.emptyList() : prescriptionPriorities;
    }

    @Override
    public PrescriptionPriority getById(Long id)
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM prescription_priorities WHERE id = %d", id)))
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

    private PrescriptionPriority parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new PrescriptionPriority(id, name);
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    @Override
    public boolean create(PrescriptionPriority prescriptionPriority)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("INSERT INTO prescription_priorities(name) values ('%s')", prescriptionPriority.getName());
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
    public boolean update(PrescriptionPriority prescriptionPriority)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("INSERT INTO prescription_priorities(first_name, last_name, patronymic, specialization_id) "
                + "values ('%s')", prescriptionPriority.getName());
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
    public boolean delete(PrescriptionPriority prescriptionPriority)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("DELETE prescription_priorities SET name = '%s' WHERE id = %d",
                prescriptionPriority.getName(), prescriptionPriority.getId());
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
