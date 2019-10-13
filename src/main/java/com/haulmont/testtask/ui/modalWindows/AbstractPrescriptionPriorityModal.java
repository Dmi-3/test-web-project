package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.db.dao.PrescriptionPriorityDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.PrescriptionPriority;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
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
    protected void initFieldsAndBind()
    {
        objectBinder = new Binder<>();

        TextField idField = addTextField("Id");
        objectBinder.forField(idField)
                .withConverter(new StringToLongConverter("Not a number"))
                .bind(PrescriptionPriority::getId, PrescriptionPriority::setId);
        idField.setVisible(false);

        TextField firstNameField = addTextField("Name");
        objectBinder.bind(firstNameField, PrescriptionPriority::getName, PrescriptionPriority::setName);
    }

    @Override
    protected boolean addNewObject()
    {
        try
        {
            PrescriptionPriority prescriptionPriority = new PrescriptionPriority();
            objectBinder.writeBean(prescriptionPriority);
            return prescriptionPriorityDao.create(prescriptionPriority);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during adding a new prescription priority.");
        }

        return false;
    }

    @Override
    protected boolean editObject()
    {
        try
        {
            PrescriptionPriority prescriptionPriority = new PrescriptionPriority();
            objectBinder.writeBean(prescriptionPriority);
            return prescriptionPriorityDao.update(prescriptionPriority);
        }
        catch (ValidationException e)
        {
            LOG.error("Error occured during updating a new prescription priority.");
        }

        return false;
    }
}
