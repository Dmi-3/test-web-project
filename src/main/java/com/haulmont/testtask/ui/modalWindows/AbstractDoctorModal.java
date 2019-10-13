package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.DoctorDao;
import com.haulmont.testtask.db.dao.SpecializationDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Specialization;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public class AbstractDoctorModal extends AbstractModalWindow<Doctor>
{
    private static final Logger LOG = Logger.getLogger(AbstractDoctorModal.class);
    private DoctorDao doctorDao;
    private SpecializationDao specializationDao;

    public AbstractDoctorModal()
    {
        super();
        specializationDao = new SpecializationDao();
        doctorDao = new DoctorDao(specializationDao);
        //Window configures
        setModal(true);
        setClosable(true);

        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }

    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField firstNameField = addTextField("Field Name");
        objectBinder.bind(firstNameField, Doctor::getFirstName, Doctor::setFirstName);

        TextField lastNameField = addTextField("Last Name");
        objectBinder.bind(lastNameField, Doctor::getLastName, Doctor::setLastName);

        TextField patronymicField = addTextField("Patronymic");
        objectBinder.bind(patronymicField, Doctor::getPatronymic, Doctor::setPatronymic);

        Grid<Specialization> specializationsGrid = new Grid<>();
        specializationsGrid.setItems(specializationDao.getAll());
        SingleSelect<Specialization> specializationField = specializationsGrid.asSingleSelect();

        objectBinder.bind(specializationField, Doctor::getSpecialization, Doctor::setSpecialization);
    }

    protected Doctor addNewObject()
    {
        Doctor doctor = new Doctor();
        try
        {
            objectBinder.writeBean(doctor);
            return doctorDao.create(doctor) ? doctor : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new doctor.");
        }

        return null;
    }

    @Override
    Doctor editObject()
    {
        return null;
    }

    protected void addSaveButton()
    {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent ->
        {
            Doctor doctor = addNewObject();
            generateNotification(doctor);
        });

        windowsLayout.addComponent(saveButton);
    }
}
