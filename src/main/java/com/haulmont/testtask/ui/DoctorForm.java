package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.DoctorDao;
import com.haulmont.testtask.db.dao.SpecializationDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdateDoctorModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddDoctorModal;
import com.vaadin.ui.Grid;

public class DoctorForm extends AbstractForm<Doctor>
{
    private DoctorDao doctorDao;

    public DoctorForm()
    {
        doctorDao = new DoctorDao(new SpecializationDao());

        generateHeaderPage("Doctors");
        generateTableObjects();
    }

    @Override
    protected void generateTableObjects()
    {
        objectsGrid = new Grid<>(Doctor.class);
        objectsGrid.setSizeFull();
        objectsGrid.setItems(doctorDao.getAll());
        objectsGrid.setColumns(DoctorForm.DoctorColumn.FIRST_NAME.getName(), DoctorForm.DoctorColumn.LAST_NAME.getName(),
                DoctorForm.DoctorColumn.PATRONYMIC.getName(), DoctorColumn.SPECIALIZATION.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        return new AddDoctorModal();
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Doctor doctor)
    {
        return new UpdateDoctorModal(doctor);
    }

    @Override
    protected void removeRow(Doctor doctor)
    {
        if (doctor == null)
        {
            return;
        }

        doctorDao.delete(doctor);
        objectsGrid.setItems(doctorDao.getAll());
    }

    public enum DoctorColumn
    {
        ID("id"),
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        PATRONYMIC("patronymic"),
        SPECIALIZATION("specialization");

        private String name;

        DoctorColumn(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}
