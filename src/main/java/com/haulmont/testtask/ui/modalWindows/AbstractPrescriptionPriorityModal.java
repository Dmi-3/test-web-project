package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.PrescriptionPriorityDao;
import com.haulmont.testtask.model.PrescriptionPriority;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

public class AbstractPrescriptionPriorityModal extends AbstractModalWindow<PrescriptionPriority>
{
    private static final Logger LOG = Logger.getLogger(AbstractPrescriptionPriorityModal.class);
    private PrescriptionPriorityDao prescriptionPriorityDao;

    public AbstractPrescriptionPriorityModal()
    {
        super();
        prescriptionPriorityDao = new PrescriptionPriorityDao();
    }

    @Override
    void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField firstNameField = addTextField("Name");
        objectBinder.bind(firstNameField, PrescriptionPriority::getName, PrescriptionPriority::setName);
    }

    @Override
    PrescriptionPriority addNewObject()
    {
        PrescriptionPriority prescriptionPriority = new PrescriptionPriority();
        try
        {
            objectBinder.writeBean(prescriptionPriority);
            return prescriptionPriorityDao.create(prescriptionPriority) ? prescriptionPriority : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription priority.");
        }

        return null;
    }

    @Override
    PrescriptionPriority editObject()
    {
        PrescriptionPriority prescriptionPriority = new PrescriptionPriority();
        try
        {
            objectBinder.writeBean(prescriptionPriority);
            return prescriptionPriorityDao.update(prescriptionPriority) ? prescriptionPriority : null;
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during updating a new prescription priority.");
        }

        return null;
    }
}
