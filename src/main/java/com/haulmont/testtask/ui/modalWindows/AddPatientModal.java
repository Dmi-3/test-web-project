package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public class AddPatientModal extends AbstractModalWindow<Patient>
{
    private static final Logger LOG = Logger.getLogger(AddPatientModal.class);
    private PatientDao patientDao;

    public AddPatientModal()
    {
        super();
        patientDao = new PatientDao();
        //Window configures
        setModal(true);
        setClosable(true);

        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
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

    private Patient addNewPatient()
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

    protected void addSaveButton()
    {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent ->
        {
            Patient patient = addNewPatient();
            generateNotification(patient);
        });

        windowsLayout.addComponent(saveButton);
    }

    protected Notification generateNotification(Patient patient)
    {
        Notification notification = new Notification("New Patient");
        notification.setDelayMsec(500);
        notification.setPosition(Position.TOP_RIGHT);

        if (patient == null)
        {
            notification.setDescription("Patient was not added because of occured errors.");
        }
        else
        {
            notification.setDescription(String.format("Patient '%s' '%s' was added successfully",
                    patient.getFirstName(), patient.getLastName()));
            this.close();
        }

        return notification;
    }

}
