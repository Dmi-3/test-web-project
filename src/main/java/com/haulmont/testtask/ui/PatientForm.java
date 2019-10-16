package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
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
        objectsGrid.setWidth("700px");
        objectsGrid.setHeightByRows(10);
        objectsGrid.setItems(patientDao.getAll());
        objectsGrid.setColumns(PatientColumn.FIRST_NAME.getName(), PatientColumn.LAST_NAME.getName(),
                PatientColumn.PATRONYMIC.getName(), PatientColumn.PHONE.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        objectsGrid.setHeightMode(HeightMode.ROW);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Patient patient)
    {
        UpdatePatientModal updatePatientModal = new UpdatePatientModal(patient);
        updatePatientModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(patientDao.getAll());
        });
        return updatePatientModal;
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        AddPatientModal addPatientModal = new AddPatientModal();
        addPatientModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(patientDao.getAll());
        });
        return addPatientModal;
    }

    @Override
    protected void removeRow(Patient patient)
    {
        if (patient == null)
        {
            return;
        }

        boolean isObjectDeleted = patientDao.delete(patient);
        generateNotification(isObjectDeleted);
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

