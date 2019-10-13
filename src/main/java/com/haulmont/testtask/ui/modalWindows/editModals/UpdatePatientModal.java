package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.AbstractPatientModal;

public class UpdatePatientModal extends AbstractPatientModal
{
    public UpdatePatientModal(Patient patient)
    {
        super();
        //Window configures
        setModal(true);
        setClosable(true);

        //Generate components
        initFieldsAndBind(patient);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
