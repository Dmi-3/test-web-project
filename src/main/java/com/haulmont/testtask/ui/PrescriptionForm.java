package com.haulmont.testtask.ui;

import com.haulmont.testtask.db.dao.*;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.haulmont.testtask.ui.modalWindows.editModals.UpdatePrescriptionModal;
import com.haulmont.testtask.ui.modalWindows.saveModals.AddPrescriptionModal;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

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
        Collection<Prescription> prescriptions = prescriptionDao.getAll();
        ListDataProvider<Prescription> dataProvider = new ListDataProvider<>(prescriptions);
        objectsGrid.setDataProvider(dataProvider);
        //objectsGrid.setItems(prescriptions);
        objectsGrid.setColumns(PrescriptionColumn.DESCRIPTION.getName(), PrescriptionColumn.CREATING_DATE.getName());
        objectsGrid.addColumn(prescription -> prescription.getPatient().getFirstName() + StringUtils.SPACE
                + prescription.getPatient().getLastName())
                .setCaption("Patient")
                .setCaption(PrescriptionColumn.PATIENT.getName());
        objectsGrid.addColumn(prescription -> prescription.getDoctor().getFirstName() + StringUtils.SPACE
                + prescription.getDoctor().getLastName())
                .setCaption("Doctor")
                .setCaption(PrescriptionColumn.DOCTOR.getName());
        objectsGrid.addColumn(prescription -> prescription.getPrescriptionPriority().getName())
                .setCaption("Priority")
                .setCaption(PrescriptionColumn.PRESCRIPTION_PRIORITY.getName());
        objectsGrid.addComponentColumn(this::generateUpdateRowButton);
        objectsGrid.addComponentColumn(this::generateRemoveRowButton);

        //filters
        TextField descriptionFilterField = new TextField("Prescription Filter");
        descriptionFilterField.addValueChangeListener(event -> dataProvider.addFilter(
                prescription -> StringUtils.containsIgnoreCase(prescription.getDescription(), descriptionFilterField.getValue())));
        descriptionFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        descriptionFilterField.setSizeFull();

        HorizontalLayout prescriptionFilterLayout = new HorizontalLayout();
        prescriptionFilterLayout.addComponent(descriptionFilterField);
        addComponent(prescriptionFilterLayout);
        addComponent(objectsGrid);
    }

    @Override
    protected AbstractModalWindow getAddModalObject()
    {
        AddPrescriptionModal addPrescriptionModal = new AddPrescriptionModal();
        addPrescriptionModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(prescriptionDao.getAll());
        });
        return addPrescriptionModal;
    }

    @Override
    protected AbstractModalWindow getUpdateModalObject(Prescription prescription)
    {
        UpdatePrescriptionModal updatePrescriptionModal = new UpdatePrescriptionModal(prescription);
        updatePrescriptionModal.addCloseListener(closeEvent ->
        {
            objectsGrid.setItems(prescriptionDao.getAll());
        });
        return updatePrescriptionModal;
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
