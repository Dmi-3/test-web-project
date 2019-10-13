package com.haulmont.testtask.ui;

import com.vaadin.ui.VerticalLayout;

import java.util.Collection;

public abstract class AbstractForm extends VerticalLayout
{
    abstract void generateHeaderPage();
    abstract void generateTableObjects();
}
