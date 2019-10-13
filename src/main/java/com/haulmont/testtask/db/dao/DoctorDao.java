package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.ConnectionService;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Specialization;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DoctorDao implements ObjectDao<Doctor>
{
    private static final Logger LOG = Logger.getLogger(DoctorDao.class);
    private SpecializationDao specializationDao;

    public DoctorDao(SpecializationDao specializationDao)
    {
        this.specializationDao = specializationDao;
    }

    @Override
    public Collection<Doctor> getAll()
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return Collections.emptyList();
        }

        ArrayList<Doctor> doctors = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctors"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Doctor doctor = parseResultSet(resultSet);
                if (doctor == null)
                {
                    continue;
                }
                doctors.add(doctor);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return doctors.isEmpty() ? Collections.emptyList() : doctors;
    }

    @Override
    public Doctor getById(Long id)
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM doctors WHERE id = %d", id)))
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

    private Doctor parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_Name");
            String lastName = resultSet.getString("last_Name");
            String patronymic = resultSet.getString("patronymic");

            Long specializationId = resultSet.getLong("specialization_id");
            Specialization specialization = specializationDao.getById(specializationId);

            return new Doctor(id, firstName, lastName, patronymic, specialization);
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    @Override
    public boolean create(Doctor doctor)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("INSERT INTO doctors(first_name, last_name, patronymic, specialization_id) "
                        + "values ('%s', '%s', '%s', %d)",
                doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpecialization().getId());
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
    public boolean update(Doctor doctor)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("UPDATE doctors SET first_name = '%s', last_name = '%s', patronymic = '%s', specialization_id = %d WHERE id = %d",
                doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpecialization().getId(), doctor.getId());
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
    public boolean delete(Doctor doctor)
    {
        Connection connection = new ConnectionService().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM doctors WHERE id = '%d'", doctor.getId())))
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
