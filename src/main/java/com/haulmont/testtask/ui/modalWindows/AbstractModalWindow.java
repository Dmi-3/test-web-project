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
        //Window configures
        setModal(true);
        setClosable(true);
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

    TextArea addTextArea(String fieldName)
    {
        HorizontalLayout fieldGroupLayout = new HorizontalLayout();
        TextArea textArea = new TextArea(fieldName);
        fieldGroupLayout.addComponent(textArea);
        windowsLayout.addComponent(fieldGroupLayout);
        return textArea;
    }

    Component addOtherComponent(Component component) // todo: rewrite to singleSelect
    {
        HorizontalLayout fieldGroupLayout = new HorizontalLayout();
        windowsLayout.addComponent(component);
        return component;
    }

    private void generateNotification(boolean isOperationSuccess)
    {
        Notification notification = new Notification("Notification");
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_RIGHT);

        if (isOperationSuccess)
        {
            notification.setDescription("Operation successfully completed");
            this.close();
        }
        else
        {
            notification.setDescription("Operation was not completed because of occured errors.");
        }

        notification.show(UI.getCurrent().getPage());
    }

    protected void addUpdateButton()
    {
        Button saveButton = new Button("Update");
        saveButton.addClickListener(clickEvent ->
        {
            boolean isObjectUpdated = editObject();
            generateNotification(isObjectUpdated);
        });

        windowsLayout.addComponent(saveButton);
    }

    protected void addSaveButton()
    {
        Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent ->
        {
            boolean isObjectCreated = addNewObject();
            generateNotification(isObjectCreated);
        });

        windowsLayout.addComponent(saveButton);
    }

    protected void initFieldsAndBind(T object)
    {
        initFieldsAndBind();
        if (object == null)
        {
            return;
        }
        objectBinder.readBean(object);
    }

    protected abstract void initFieldsAndBind();

    protected abstract boolean addNewObject();

    protected abstract boolean editObject();

}
