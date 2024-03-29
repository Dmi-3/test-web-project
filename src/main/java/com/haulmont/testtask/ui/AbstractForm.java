package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractForm<T> extends VerticalLayout
{
    Grid<T> objectsGrid;

    void generateHeaderPage(String pageLabelName)
    {
        HorizontalLayout headerPatientView = new HorizontalLayout();

        Label pageLabel = new Label(pageLabelName);
        headerPatientView.addComponents(pageLabel);

        Button addPatientModal = new Button("Create New");
        addPatientModal.addClickListener(click ->
        {
            UI.getCurrent().addWindow(getAddModalObject());
        });

        headerPatientView.addComponent(addPatientModal);
        addComponent(headerPatientView);
    }

    protected abstract void generateTableObjects();

    Button generateUpdateRowButton(T object)
    {
        Button button = new Button(VaadinIcons.EDIT);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(clickEvent ->
        {
            UI.getCurrent().addWindow(getUpdateModalObject(object));
        });

        return button;
    }

    protected abstract AbstractModalWindow getAddModalObject();

    protected abstract AbstractModalWindow getUpdateModalObject(T object);

    Button generateRemoveRowButton(T object)
    {
        Button button = new Button(VaadinIcons.CLOSE_SMALL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(clickEvent ->
        {
            removeRow(object);
        });

        return button;
    }

    protected void generateNotification(boolean isOperationSuccess)
    {
        Notification notification = new Notification("");
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_RIGHT);

        if (isOperationSuccess)
        {
            notification.setDescription("Operation successfully completed");
        }
        else
        {
            notification.setDescription("Operation was not completed because of occured errors.");
        }

        notification.show(UI.getCurrent().getPage());
    }

    protected abstract void removeRow(T object);
}
