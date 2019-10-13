package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPatientModal;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public abstract class AbstractPatientModal extends AbstractModalWindow<Patient>
{
    private static final Logger LOG = Logger.getLogger(AddPatientModal.class);
    private PatientDao patientDao;

    public AbstractPatientModal()
    {
        super();
        patientDao = new PatientDao();
    }

    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField firstNameField = addTextField("Field Name");
        objectBinder.bind(firstNameField, Patient::getFirstName, Patient::setFirstName);

        TextField lastNameField = addTextField("Last Name");
        objectBinder.bind(lastNameField, Patient::getLastName, Patient::setLastName);

        TextField patronymicField = addTextField("Patronymic");
        objectBinder.bind(patronymicField, Patient::getPatronymic, Patient::setPatronymic);

        TextField phoneField = addTextField("Phone");
        objectBinder.bind(phoneField, Patient::getPhone, Patient::setPhone);
    }

    protected Patient addNewObject()
    {
        Patient patient = new Patient();
        try
        {
            objectBinder.writeBean(patient);
            return patientDao.create(patient) ? patient : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new patient.");
        }

        return null;
    }

    @Override
    Patient editObject()
    {
        Patient patient = new Patient();
        try
        {
            objectBinder.writeBean(patient);
            return patientDao.update(patient) ? patient : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new patient.");
        }

        return null;
    }
}
