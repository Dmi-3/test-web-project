package com.haulmont.testtask.ui.modalWindows.editModals;

import com.haulmont.testtask.model.PrescriptionPriority;
import com.haulmont.testtask.ui.modalWindows.AbstractPrescriptionPriorityModal;

public class UpdatePrescriptionPriorityModal extends AbstractPrescriptionPriorityModal
{
    public UpdatePrescriptionPriorityModal(PrescriptionPriority prescriptionPriority)
    {
        super();
        //Generate components
        initFieldsAndBind(prescriptionPriority);
        addUpdateButton();

        setContent(windowsLayout);
    }
}
