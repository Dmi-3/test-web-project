package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.ConnectionService;
import com.haulmont.testtask.model.Patient;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PatientDao implements ObjectDao<Patient>
{
    private static final Logger LOG = Logger.getLogger(PatientDao.class);

    @Override
    public Collection<Patient> getAll()
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            LOG.error("Connection with DB is failed.");
            return Collections.emptyList();
        }

        ArrayList<Patient> patients = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patients"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Patient patient = parseResultSet(resultSet);
                if (patient == null)
                {
                    continue;
                }
                patients.add(patient);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return patients.isEmpty() ? Collections.emptyList() : patients;
    }

    @Override
    public Patient getById(Long id)
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM patients WHERE id = %d", id)))
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

    private Patient parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_Name");
            String lastName = resultSet.getString("last_Name");
            String patronymic = resultSet.getString("patronymic");
            String phone = resultSet.getString("phone");

            return new Patient(id, firstName, lastName, patronymic, phone);
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    @Override
    public boolean create(Patient patient)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("INSERT INTO patients(first_name, last_name, patronymic, phone) VALUES ('%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getLastName(), patient.getPatronymic(), patient.getPhone());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateRequest))
        {
            return preparedStatement.execute();
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }

    @Override
    public boolean update(Patient patient)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("UPDATE patients SET first_name = '%s', last_name = '%s', patronymic = '%s', phone = '%s' WHERE id = %d",
                patient.getFirstName(), patient.getLastName(), patient.getPatronymic(), patient.getPhone(), patient.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateRequest))
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
    public boolean delete(Patient patient)
    {
        Connection connection = new ConnectionService().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM patients WHERE id = '%d')", patient.getId())))
        {
            return preparedStatement.execute();
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }
}
