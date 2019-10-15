package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.ui.modalWindows.AbstractSpecializationModal;

public class AddSpecializationModal extends AbstractSpecializationModal
{
    public AddSpecializationModal()
    {
        super();
        //Generate components
        initFieldsAndBind();
        addSaveButton();

        setContent(windowsLayout);
    }
}
