package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.db.dao.PatientDao;
import com.haulmont.testtask.ui.modalWindows.AbstractPatientModal;
import org.apache.log4j.Logger;

public class AddPatientModal extends AbstractPatientModal
{
    public AddPatientModal()
    {
        super();
        //Window configures
        setModal(true);
        setClosable(true);

        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }
}
