package com.haulmont.testtask.ui.modalWindows;

import com.vaadin.data.Binder;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

public abstract class AbstractModalWindow<T> extends Window
{
    protected Binder<T> objectBinder;
    protected VerticalLayout windowsLayout;

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

    void generateNotification(T object)
    {
        Notification notification = new Notification("Notification");
        notification.setDelayMsec(500);
        notification.setPosition(Position.TOP_RIGHT);

        if (object == null)
        {
            notification.setDescription("Operation was not completed because of occured errors.");
        }
        else
        {
            notification.setDescription("Operation successfully completed");
            this.close();
        }

        notification.show(UI.getCurrent().getPage());
    }

    void addUpdateButton()
    {
        Button saveButton = new Button("Update");
        saveButton.addClickListener(clickEvent ->
        {
            T object = editObject();
            generateNotification(object);
        });

        windowsLayout.addComponent(saveButton);
    }

    protected void addSaveButton()
    {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent ->
        {
            T object = addNewObject();
            generateNotification(object);
        });

        windowsLayout.addComponent(saveButton);
    }

    abstract void initFieldsAndBind();

    abstract T addNewObject();

    abstract T editObject();

}
