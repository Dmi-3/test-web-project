package com.haulmont.testtask.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DoctorForm extends VerticalLayout
{
    public DoctorForm()
    {
        Label pageLabel = new Label("Doctors");
        addComponent(pageLabel);

        Grid doctorsGrid = new Grid();

    }
}
