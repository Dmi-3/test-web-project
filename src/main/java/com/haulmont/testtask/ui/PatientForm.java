package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdatePatientModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPatientModal;

public class PatientForm extends AbstractForm<Patient>
{
    private PatientDao patientDao;

    public PatientForm()
    {
        patientDao = new PatientDao();

        generateHeaderPage("Patients");
        generateTableObjects();
    }

    void generateTableObjects()
    {
        objectsGrid.setItems(patientDao.getAll());
        objectsGrid.removeColumn(PatientColumn.ID.getName());
        objectsGrid.setColumns(PatientColumn.FIRST_NAME.getName(), PatientColumn.LAST_NAME.getName(),
                PatientColumn.PATRONYMIC.getName(), PatientColumn.PHONE.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        addComponent(objectsGrid);
    }

    @Override
    AbstractModalWindow getUpdateModalObject(Patient object)
    {
        return new UpdatePatientModal(object);
    }

    @Override
    AbstractModalWindow getAddModalObject()
    {
        return new AddPatientModal();
    }

    @Override
    void removeRow(Patient patient)
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

