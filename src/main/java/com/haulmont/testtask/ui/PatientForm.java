package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatientForm extends VerticalLayout
{
    private PatientDao patientDao;

    public PatientForm()
    {
        patientDao = new PatientDao();

        setSizeFull();
        Label pageLabel = new Label("Patients");
        addComponent(pageLabel);

        Collection<Patient> patientsList = patientDao.getAll();

        Grid<Patient> patientsGrid = new Grid<>(Patient.class);
        patientsGrid.setItems(patientsList);
        patientsGrid.removeColumn("id");
        patientsGrid.setColumns("firstName", "lastName", "patronymic", "phone");
        addComponent(patientsGrid);
    }
}
