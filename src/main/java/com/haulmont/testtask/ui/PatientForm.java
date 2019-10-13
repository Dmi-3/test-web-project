package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPatientModal;
import com.vaadin.ui.*;

public class PatientForm extends AbstractForm
{
    private PatientDao patientDao;

    public PatientForm()
    {
        patientDao = new PatientDao();

        generateHeaderPage();
        generateTableObjects();
    }

    void generateHeaderPage()
    {
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
    }

    void generateTableObjects()
    {
        Grid<Patient> patientsGrid = new Grid<>(Patient.class);
        patientsGrid.setItems(patientDao.getAll());
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

