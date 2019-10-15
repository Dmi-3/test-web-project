package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.modalWindows.AbstractDoctorModal;

public class AddDoctorModal extends AbstractDoctorModal
{
    public AddDoctorModal()
    {
        super();
        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }
}
