package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.AddPatientModal;
import com.vaadin.ui.*;

import java.util.Collection;

public class PatientForm extends AbstractForm<Patient>
{
    private PatientDao patientDao;

    public PatientForm()
    {
        patientDao = new PatientDao();

        HorizontalLayout headerPatientView = new HorizontalLayout();
        Label pageLabel = new Label("Patients");
        headerPatientView.addComponents(pageLabel);

        Button addPatientModal = new Button("Add Patient");
        addPatientModal.addClickListener(click ->
        {
            UI.getCurrent().addWindow(new AddPatientModal());
        });
        headerPatientView.addComponent(addPatientModal);
        addComponent(headerPatientView);

        Collection<Patient> patientsList = patientDao.getAll();
        generateTableObjects(patientsList);

    }

    void generateTableObjects(Collection<Patient> patientsList)
    {
        Grid<Patient> patientsGrid = new Grid<>(Patient.class);
        patientsGrid.setItems(patientsList);
        patientsGrid.removeColumn(PatientColumn.ID.getName());
        patientsGrid.setColumns(PatientColumn.FIRST_NAME.getName(), PatientColumn.LAST_NAME.getName(),
                PatientColumn.PATRONYMIC.getName(), PatientColumn.PHONE.getName());
        addComponent(patientsGrid);
    }

    public enum PatientColumn
    {
        ID("id"),
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        PATRONYMIC("patronymic"),
        PHONE("phone");

        private String name;

        PatientColumn(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}

