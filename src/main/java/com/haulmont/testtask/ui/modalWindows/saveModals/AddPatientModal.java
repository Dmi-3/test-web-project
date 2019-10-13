package com.haulmont.testtask.ui.modalWindows.saveModals;

import com.haulmont.testtask.ui.modalWindows.AbstractPatientModal;

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
