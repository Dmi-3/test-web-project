package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.ui.modalWindows.AbstractPrescriptionPriorityModal;

public class AddPrescriptionPriorityModal extends AbstractPrescriptionPriorityModal
{
    public AddPrescriptionPriorityModal()
    {
        super();
        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }
}
