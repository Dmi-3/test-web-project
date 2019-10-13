package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.*;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.PrescriptionPriority;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import org.apache.log4j.Logger;

public class AbstractPrescriptionModal extends AbstractModalWindow<Prescription>
{
    private static final Logger LOG = Logger.getLogger(AbstractPrescriptionModal.class);

    private PatientDao patientDao;
    private DoctorDao doctorDao;
    private PrescriptionPriorityDao prescriptionPriorityDao;
    private PrescriptionDao prescriptionDao;

    public AbstractPrescriptionModal()
    {
        super();
        patientDao = new PatientDao();
        doctorDao = new DoctorDao(new SpecializationDao());
        prescriptionPriorityDao = new PrescriptionPriorityDao();
        prescriptionDao = new PrescriptionDao(patientDao, doctorDao, prescriptionPriorityDao);
    }

    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextArea descriptionField = addTextArea("Description");
        objectBinder.bind(descriptionField, Prescription::getDescription, Prescription::setDescription);

        ComboBox<Patient> patientsField = new ComboBox<>();
        patientsField.setItems(patientDao.getAll());
        objectBinder.bind(patientsField, Prescription::getPatient, Prescription::setPatient);

        ComboBox<Doctor> doctorsField = new ComboBox<>();
        doctorsField.setItems(doctorDao.getAll());
        objectBinder.bind(doctorsField, Prescription::getDoctor, Prescription::setDoctor);

        ComboBox<PrescriptionPriority> prescriptionPriorityField = new ComboBox<>();
        doctorsField.setItems(doctorDao.getAll());
        objectBinder.bind(prescriptionPriorityField, Prescription::getPrescriptionPriority, Prescription::setPrescriptionPriority);
    }

    protected Prescription addNewObject()
    {
        Prescription prescription = new Prescription();
        try
        {
            objectBinder.writeBean(prescription);
            return prescriptionDao.create(prescription) ? prescription : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription.");
        }

        return null;
    }

    @Override
    protected Prescription editObject()
    {
        Prescription prescription = new Prescription();
        try
        {
            objectBinder.writeBean(prescription);
            return prescriptionDao.update(prescription) ? prescription : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription.");
        }

        return null;
    }
}
