package com.haulmont.testtask.ui.modalWindows;

import com.haulmont.testtask.model.Patient;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public abstract class AbstractModalWindow<T> extends Window
{
    Binder<Patient> objectBinder;
    VerticalLayout windowsLayout;

    AbstractModalWindow()
    {
        windowsLayout = new VerticalLayout();
    }

    TextField addTextField(String fieldName)
    {
        HorizontalLayout fieldGroupLayout = new HorizontalLayout();
        TextField textField = new TextField(fieldName);
        fieldGroupLayout.addComponent(textField);
        windowsLayout.addComponent(fieldGroupLayout);
        return textField;
    }

    abstract void initFieldsAndBind();

    abstract Notification generateNotification(T object);
    abstract void addSaveButton();


}
