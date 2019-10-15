package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.ui.modalWindows.AbstractPrescriptionModal;

public class AddPrescriptionModal extends AbstractPrescriptionModal
{
    public AddPrescriptionModal()
    {
        super();
        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }
}
