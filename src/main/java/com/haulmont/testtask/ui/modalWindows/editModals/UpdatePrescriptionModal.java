package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.modalWindows.AbstractPrescriptionModal;

public class UpdatePrescriptionModal extends AbstractPrescriptionModal
{
    public UpdatePrescriptionModal(Prescription prescription)
    {
        super();
        //Generate components
        initFieldsAndBind(prescription);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
