package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.modalWindows.AbstractModalWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractForm<T> extends VerticalLayout
{
    Grid<T> objectsGrid = new Grid<>();

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

    abstract AbstractModalWindow getAddModalObject();

    abstract void generateTableObjects();

    Button generateUpdateRowButton(T object)
    {
        Button button = new Button(VaadinIcons.CLOSE_SMALL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(clickEvent ->
        {
            UI.getCurrent().addWindow(getUpdateModalObject(object));
        });

        return button;
    }

    abstract AbstractModalWindow getUpdateModalObject(T object);

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

    abstract void removeRow(T object);


}