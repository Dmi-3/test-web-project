package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.*;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdatePrescriptionModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPrescriptionModal;
import com.vaadin.ui.Grid;

public class PrescriptionForm extends AbstractForm<Prescription>
{
    private PrescriptionDao prescriptionDao;

    public PrescriptionForm()
    {
        prescriptionDao = new PrescriptionDao(new PatientDao(), new DoctorDao(new SpecializationDao()), new PrescriptionPriorityDao());

        generateHeaderPage("Prescriptions");
        generateTableObjects();
    }

    @Override
    protected void generateTableObjects()
    {
        objectsGrid = new Grid<>(Prescription.class);
        objectsGrid.setWidth("800px");
        objectsGrid.setItems(prescriptionDao.getAll());
        objectsGrid.removeColumn(PrescriptionColumn.ID.getName());
        objectsGrid.setColumns(PrescriptionColumn.DESCRIPTION.getName(), PrescriptionColumn.PATIENT.getName(),
                PrescriptionColumn.DOCTOR.getName(), PrescriptionColumn.CREATING_DATE.getName(),
                PrescriptionColumn.PRESCRIPTION_PRIORITY.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        return new AddPrescriptionModal();
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Prescription prescription)
    {
        return new UpdatePrescriptionModal(prescription);
    }

    @Override
    protected void removeRow(Prescription prescription)
    {
        if (prescription == null)
        {
            return;
        }

        prescriptionDao.delete(prescription);
        objectsGrid.setItems(prescriptionDao.getAll());
    }

    public enum PrescriptionColumn
    {
        ID("id"),
        DESCRIPTION("description"),
        PATIENT("patient"),
        DOCTOR("doctor"),
        CREATING_DATE("creatingDate"),
        PRESCRIPTION_PRIORITY("prescriptionPriority");

        private String name;

        PrescriptionColumn(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}
