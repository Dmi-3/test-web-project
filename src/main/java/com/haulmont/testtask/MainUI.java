package com.haulmont.testtask;

import com.haulmont.testtask.ui.DoctorForm;
import com.haulmont.testtask.ui.PatientForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI
{
    private VerticalLayout mainLayout;

    @Override
    protected void init(VaadinRequest request)
    {
        //Configures
        setSizeFull();
        //
        mainLayout = new VerticalLayout();
        addHeader();
        addCenter();
        addFooter();
        setContent(mainLayout);
    }

    private void addHeader()
    {
        HorizontalLayout header = new HorizontalLayout();
        mainLayout.addComponent(header);
    }


    private void addCenter()
    {
        VerticalLayout content = new VerticalLayout();
        VerticalLayout navBar = new VerticalLayout();

        Button doctorFormBtn = new Button("Doctors");
        doctorFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new DoctorForm());
        });

        Button doctorFormBtn2 = new Button();
        doctorFormBtn2.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PatientForm());
        });
        navBar.addComponents(doctorFormBtn, doctorFormBtn2);
        HorizontalLayout center = new HorizontalLayout();
        center.addComponents(navBar, content);
        mainLayout.addComponent(center);
    }

    private void addFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        mainLayout.addComponent(footer);
    }
}