package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.*;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.PrescriptionPriority;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;
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

        TextField idField = addTextField("Id");
        objectBinder.forField(idField)
                .withConverter(new StringToLongConverter("Not a number"))
                .bind(Prescription::getId, Prescription::setId);
        idField.setVisible(false);

        TextArea descriptionField = addTextArea("Description");
        objectBinder.forField(descriptionField)
                .asRequired()
                .bind(Prescription::getDescription, Prescription::setDescription);

        ComboBox<Patient> patientField = addComboBox("Patient", patientDao.getAll());
        patientField.setItemCaptionGenerator(patient -> patient.getFirstName() + StringUtils.SPACE + patient.getLastName());
        objectBinder.forField(patientField)
                .asRequired()
                .bind(Prescription::getPatient, Prescription::setPatient);

        ComboBox<Doctor> doctorField = addComboBox("Doctor", doctorDao.getAll());
        doctorField.setItemCaptionGenerator(doctor -> doctor.getFirstName() + StringUtils.SPACE + doctor.getLastName());
        objectBinder.forField(doctorField)
                .asRequired()
                .bind(Prescription::getDoctor, Prescription::setDoctor);

        ComboBox<PrescriptionPriority> prescriptionPriorityField = addComboBox("Prescription Priority", prescriptionPriorityDao.getAll());
        prescriptionPriorityField.setItemCaptionGenerator(PrescriptionPriority::getName);
        objectBinder.forField(prescriptionPriorityField)
                .asRequired()
                .bind(Prescription::getPrescriptionPriority, Prescription::setPrescriptionPriority);
    }

    protected boolean addNewObject()
    {
        try
        {
            Prescription prescription = new Prescription();
            objectBinder.writeBean(prescription);
                return prescriptionDao.create(prescription);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription.");
        }

        return false;
    }

    @Override
    protected boolean editObject()
    {
        try
        {
            Prescription prescription = new Prescription();
            objectBinder.writeBean(prescription);
            return prescriptionDao.update(prescription);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription.");
        }

        return false;
    }
}
