package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.Specialization;
import com.haulmont.testtask.ui.modalWindows.AbstractSpecializationModal;

public class UpdateSpecializationModal extends AbstractSpecializationModal
{
    public UpdateSpecializationModal(Specialization specialization)
    {
        super();
        //Generate components
        initFieldsAndBind(specialization);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
