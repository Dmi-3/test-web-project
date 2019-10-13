package com.haulmont.testtask.db.dao;

import com.haulmont.testtask.db.ConnectionService;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.PrescriptionPriority;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PrescriptionDao implements ObjectDao<Prescription>
{
    private static final Logger LOG = Logger.getLogger(PrescriptionDao.class);
    private final PatientDao patientDao;
    private final DoctorDao doctorDao;
    private final PrescriptionPriorityDao prescriptionPriorityDao;

    public PrescriptionDao(PatientDao patientDao, DoctorDao doctorDao, PrescriptionPriorityDao prescriptionPriorityDao)
    {
        this.patientDao = patientDao;
        this.doctorDao = doctorDao;
        this.prescriptionPriorityDao = prescriptionPriorityDao;
    }

    @Override
    public Collection<Prescription> getAll()
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return Collections.emptyList();
        }

        ArrayList<Prescription> prescriptions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM prescriptions"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Prescription prescription = parseResultSet(resultSet);
                if (prescription == null)
                {
                    continue;
                }
                prescriptions.add(prescription);
            }
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return prescriptions.isEmpty() ? Collections.emptyList() : prescriptions;
    }

    @Override
    public Prescription getById(Long id)
    {
        Connection connection = new ConnectionService().getConnection();
        if (connection == null)
        {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM prescriptions WHERE id = %d", id)))
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

    private Prescription parseResultSet(ResultSet resultSet)
    {
        try
        {
            Long id = resultSet.getLong("id");
            String description = resultSet.getString("description");

            Long patientId = resultSet.getLong("patient_id");
            Patient patient = patientDao.getById(patientId);

            Long doctorId = resultSet.getLong("doctor_id");
            Doctor doctor = doctorDao.getById(doctorId);

            Date creatingDate = resultSet.getDate("creating_date");

            Long prescriptionPriorityId = resultSet.getLong("prescription_priority_id bigint");
            PrescriptionPriority prescriptionPriority = prescriptionPriorityDao.getById(prescriptionPriorityId);

            return new Prescription(id, description, patient, doctor, creatingDate, prescriptionPriority);
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }

        return null;
    }

    @Override
    public boolean create(Prescription prescription)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlCreateRequest = String.format("INSERT INTO prescriptions(description, patient_id, doctor_id, creating_date, prescription_priority_id) "
                        + "values ('%s', '%s', '%s', '%s', '%s')",
                prescription.getDescription(), prescription.getPatient().getId(), prescription.getDoctor().getId(),
                prescription.getCreatingDate(), prescription.getPrescriptionPriority().getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateRequest))
        {
            preparedStatement.execute();
        }
        catch (SQLException ex)
        {
            LOG.error("Error occured during getting info from DataBase.", ex);
        }
        return false;
    }

    @Override
    public boolean update(Prescription prescription)
    {
        Connection connection = new ConnectionService().getConnection();
        String sqlUpdateRequest = String.format("UPDATE prescriptions SET description = '%s', patient_id = '%d', doctor_id = '%d',"
                        + " creating_date = '%s', prescription_priority_id = '%d' WHERE id = %d",
                prescription.getDescription(), prescription.getPatient().getId(), prescription.getDoctor().getId(),
                prescription.getCreatingDate(), prescription.getPrescriptionPriority().getId(), prescription.getId());

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
    public boolean delete(Prescription prescription)
    {
        Connection connection = new ConnectionService().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM prescriptions WHERE id = '%d'", prescription.getId())))
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
