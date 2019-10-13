package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.DoctorDao;
import com.haulmont.testtask.db.dao.SpecializationDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Specialization;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public abstract class AbstractDoctorModal extends AbstractModalWindow<Doctor>
{
    private static final Logger LOG = Logger.getLogger(AbstractDoctorModal.class);
    private DoctorDao doctorDao;
    private SpecializationDao specializationDao;

    public AbstractDoctorModal()
    {
        super();
        specializationDao = new SpecializationDao();
        doctorDao = new DoctorDao(specializationDao);
    }

    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField idField = addTextField("Id");
        objectBinder.forField(idField)
                .withConverter(new StringToLongConverter("Not a number"))
                .bind(Doctor::getId, Doctor::setId);
        idField.setVisible(false);

        TextField firstNameField = addTextField("Field Name");
        objectBinder.bind(firstNameField, Doctor::getFirstName, Doctor::setFirstName);

        TextField lastNameField = addTextField("Last Name");
        objectBinder.bind(lastNameField, Doctor::getLastName, Doctor::setLastName);

        TextField patronymicField = addTextField("Patronymic");
        objectBinder.bind(patronymicField, Doctor::getPatronymic, Doctor::setPatronymic);

        ComboBox<Specialization> specializationsField = new ComboBox<>();
        specializationsField.setItems(specializationDao.getAll());
        objectBinder.bind(specializationsField, Doctor::getSpecialization, Doctor::setSpecialization);
    }

    protected boolean addNewObject()
    {
        try
        {
            Doctor doctor = new Doctor();
            objectBinder.writeBean(doctor);
            return doctorDao.create(doctor);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new doctor.");
        }

        return false;
    }

    @Override
    protected boolean editObject()
    {
        try
        {
            Doctor doctor = new Doctor();
            objectBinder.writeBean(doctor);
            return doctorDao.update(doctor);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new doctor.");
        }

        return false;
    }

}
