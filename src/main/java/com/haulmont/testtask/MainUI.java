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
        header.addComponent(new Label("Haulmont Hospital"));
        mainLayout.addComponent(header);
    }

    private void addCenter()
    {
        VerticalLayout content = new VerticalLayout();
        VerticalLayout navBar = new VerticalLayout();

        Button doctorsFormBtn = new Button("Doctors");
        doctorsFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new DoctorForm());
        });

        Button patientsFormBtn = new Button("Patients");
        patientsFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PatientForm());
        });

        Button prescriptionstFormBtn = new Button("Prescriptions");
        prescriptionstFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PatientForm());
        });

        Button prescriptionPrioritiesFormBtn = new Button("Prescription Priorities");
        prescriptionstFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PatientForm());
        });

        Button specializations = new Button("Specializations");
        prescriptionstFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PatientForm());
        });

        navBar.addComponents(doctorsFormBtn, patientsFormBtn, prescriptionstFormBtn, prescriptionPrioritiesFormBtn, specializations);
        HorizontalLayout center = new HorizontalLayout();
        center.addComponents(navBar, content);
        mainLayout.addComponent(center);
    }

    private void addFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        Label footerText = new Label("Author: Dmitrii Mikhailov");
        footer.addComponent(footerText);
        footer.setComponentAlignment(footerText,  Alignment.TOP_RIGHT);
        mainLayout.addComponent(footer);
    }
}