package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdatePatientModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPatientModal;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;

public class PatientForm extends AbstractForm<Patient>
{
    private PatientDao patientDao;

    public PatientForm()
    {
        super();
        patientDao = new PatientDao();

        generateHeaderPage("Patients");
        generateTableObjects();
    }

    protected void generateTableObjects()
    {
        objectsGrid = new Grid<>(Patient.class);
        objectsGrid.setItems(patientDao.getAll());
        objectsGrid.setColumns(PatientColumn.FIRST_NAME.getName(), PatientColumn.LAST_NAME.getName(),
                PatientColumn.PATRONYMIC.getName(), PatientColumn.PHONE.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        objectsGrid.setHeightMode(HeightMode.ROW);
        objectsGrid.setWidth("800px");
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Patient object)
    {
        return new UpdatePatientModal(object);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        return new AddPatientModal();
    }

    @Override
    protected void removeRow(Patient patient)
    {
        if (patient == null)
        {
            return;
        }

        patientDao.delete(patient);
        objectsGrid.setItems(patientDao.getAll());
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

