package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPatientModal;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
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

        TextField idField = addTextField("Id");
        objectBinder.forField(idField)
                .withConverter(new StringToLongConverter("Not a number"))
                .bind(Patient::getId, Patient::setId);
        idField.setVisible(false);

        TextField firstNameField = addTextField("Field Name");
        objectBinder.forField(firstNameField)
                .asRequired()
                .bind(Patient::getFirstName, Patient::setFirstName);

        TextField lastNameField = addTextField("Last Name");
        objectBinder.forField(lastNameField)
                .asRequired()
                .bind(Patient::getLastName, Patient::setLastName);

        TextField patronymicField = addTextField("Patronymic");
        objectBinder.bind(patronymicField, Patient::getPatronymic, Patient::setPatronymic);

        TextField phoneField = addTextField("Phone");
        objectBinder.forField(phoneField)
                .asRequired()
                .bind(Patient::getPhone, Patient::setPhone);
    }

    protected boolean addNewObject()
    {
        try
        {
            Patient patient = new Patient();
            objectBinder.writeBean(patient);
            return patientDao.create(patient);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new patient.");
        }

        return false;
    }

    @Override
    protected boolean editObject()
    {
        Patient patient = new Patient();
        try
        {
            objectBinder.writeBean(patient);
            return patientDao.update(patient);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new patient.");
        }

        return false;
    }
}
