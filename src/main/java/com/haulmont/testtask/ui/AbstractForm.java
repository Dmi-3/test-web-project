package com.haulmont.testtask.ui;

import com.vaadin.ui.VerticalLayout;

import java.util.Collection;

public abstract class AbstractForm<T> extends VerticalLayout
{
    abstract void generateTableObjects(Collection<T> listObjects);
}
