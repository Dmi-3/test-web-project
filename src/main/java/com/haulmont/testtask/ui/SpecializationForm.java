package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.SpecializationDao;
import com.haulmont.testtask.model.Specialization;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdateSpecializationModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddSpecializationModal;
import com.vaadin.ui.Grid;

public class SpecializationForm extends AbstractForm<Specialization>
{
    private SpecializationDao specializationDao;

    public SpecializationForm()
    {
        specializationDao = new SpecializationDao();

        generateHeaderPage("Specializations");
        generateTableObjects();
        setWidth("100%");
    }

    @Override
    protected void generateTableObjects()
    {
        objectsGrid = new Grid<>(Specialization.class);
        objectsGrid.setSizeFull();
        objectsGrid.setHeightByRows(10);
        objectsGrid.setItems(specializationDao.getAll());
        objectsGrid.setColumns(SpecializationColumn.NAME.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        AddSpecializationModal addSpecializationModal = new AddSpecializationModal();
        addSpecializationModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(specializationDao.getAll());
        });
        return addSpecializationModal;
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Specialization specialization)
    {
        UpdateSpecializationModal updateSpecializationModal = new UpdateSpecializationModal(specialization);
        updateSpecializationModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(specializationDao.getAll());
        });
        return updateSpecializationModal;
    }

    @Override
    protected void removeRow(Specialization specialization)
    {
        if (specialization == null)
        {
            return;
        }

        boolean isObjectDeleted = specializationDao.delete(specialization);
        generateNotification(isObjectDeleted);
        objectsGrid.setItems(specializationDao.getAll());
    }

    public enum SpecializationColumn
    {
        ID("id"),
        NAME("name");

        private String name;

        SpecializationColumn(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}
