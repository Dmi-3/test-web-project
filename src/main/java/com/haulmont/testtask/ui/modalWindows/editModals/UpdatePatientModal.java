package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.modalWindows.AbstractPatientModal;

public class UpdatePatientModal extends AbstractPatientModal
{
    public UpdatePatientModal(Patient patient)
    {
        super();
        //Generate components
        initFieldsAndBind(patient);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
