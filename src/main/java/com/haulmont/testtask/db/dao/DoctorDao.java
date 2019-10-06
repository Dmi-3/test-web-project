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
        ConnectionService connectionService = new ConnectionService();
        Connection connection = connectionService.getConnection();
        if (connection == null)
        {
            return Collections.emptyList();
        }

        ArrayList<Doctor> doctors = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from doctors"))
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
        return null;
    }

    private Doctor parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String patronymic = resultSet.getString("patronymic");

            Long specializationId = resultSet.getLong("specialization_id");
            Specialization specialization = specializationDao.getById(specializationId);

            return new Doctor(id, firstName, lastName, patronymic, specialization);
        }
        catch (SQLException e)
        {
            LOG.error("Error occured during getting info from DataBase.");
        }

        return null;
    }

    @Override
    public boolean create(Doctor object)
    {
        return false;
    }

    @Override
    public boolean update(Doctor object)
    {
        return false;
    }

    @Override
    public boolean delete(Doctor object)
    {
        return false;
    }
}
