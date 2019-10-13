package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.modalWindows.AbstractDoctorModal;

public class UpdateDoctorModal extends AbstractDoctorModal
{
    public UpdateDoctorModal(Doctor doctor)
    {
        super();
        //Generate components
        initFieldsAndBind(doctor);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
