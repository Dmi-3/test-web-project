package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.SpecializationDao;
import com.haulmont.testtask.model.Specialization;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public class AbstractSpecializationModal extends AbstractModalWindow<Specialization>
{
    private static final Logger LOG = Logger.getLogger(AbstractSpecializationModal.class);

    private SpecializationDao specializationDao;

    public AbstractSpecializationModal()
    {
        super();
        specializationDao = new SpecializationDao();
    }

    @Override
    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField firstNameField = addTextField("Name");
        objectBinder.bind(firstNameField, Specialization::getName, Specialization::setName);
    }

    @Override
    protected boolean addNewObject()
    {
        Specialization specialization = new Specialization();
        try
        {
            objectBinder.writeBean(specialization);
            return specializationDao.create(specialization);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new specialization.");
        }

        return false;
    }

    @Override
    protected boolean editObject()
    {
        try
        {
            Specialization specialization = new Specialization();
            objectBinder.writeBean(specialization);
            return specializationDao.create(specialization);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during updating a new specialization.");
        }

        return false;
    }
}
