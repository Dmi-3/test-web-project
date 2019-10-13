package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.PrescriptionPriorityDao;
import com.haulmont.testtask.model.PrescriptionPriority;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdatePrescriptionPriorityModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPrescriptionPriorityModal;
import com.vaadin.ui.Grid;

public class PrescriptionPriorityForm extends AbstractForm<PrescriptionPriority>
{
    private PrescriptionPriorityDao prescriptionPriorityDao;

    public PrescriptionPriorityForm()
    {
        prescriptionPriorityDao = new PrescriptionPriorityDao();

        generateHeaderPage("Prescription Priorities");
        generateTableObjects();
    }

    @Override
    protected void generateTableObjects()
    {
        objectsGrid = new Grid<>(PrescriptionPriority.class);
        objectsGrid.setWidth("800px");
        objectsGrid.setItems(prescriptionPriorityDao.getAll());
        objectsGrid.removeColumn(PrescriptionColumn.ID.getName());
        objectsGrid.setColumns(PrescriptionColumn.NAME.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        AddPrescriptionPriorityModal addPrescriptionPriorityModal = new AddPrescriptionPriorityModal();
        addPrescriptionPriorityModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(prescriptionPriorityDao.getAll());
        });
        return addPrescriptionPriorityModal;
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(PrescriptionPriority prescriptionPriority)
    {
        UpdatePrescriptionPriorityModal updatePrescriptionPriorityModal = new UpdatePrescriptionPriorityModal(prescriptionPriority);
        updatePrescriptionPriorityModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(prescriptionPriorityDao.getAll());
        });
        return updatePrescriptionPriorityModal;
    }

    @Override
    protected void removeRow(PrescriptionPriority prescriptionPriority)
    {
        if (prescriptionPriority == null)
        {
            return;
        }

        prescriptionPriorityDao.delete(prescriptionPriority);
        objectsGrid.setItems(prescriptionPriorityDao.getAll());
    }

    public enum PrescriptionColumn
    {
        ID("id"),
        NAME("name");

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
