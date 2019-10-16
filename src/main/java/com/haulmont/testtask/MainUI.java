package com.haulmont.testtask;

import com.haulmont.testtask.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI
{
    @Override
    protected void init(VaadinRequest request)
    {
        VerticalLayout mainLayout = new VerticalLayout();
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
            content.addComponent(new PrescriptionForm());
        });

        Button prescriptionPrioritiesFormBtn = new Button("Prescription Priorities");
        prescriptionPrioritiesFormBtn.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new PrescriptionPriorityForm());
        });

        Button specializations = new Button("Specializations");
        specializations.addClickListener(clickEvent ->
        {
            content.removeAllComponents();
            content.addComponent(new SpecializationForm());
        });

        navBar.addComponents(new Label("Haulmont Hospital"), doctorsFormBtn, patientsFormBtn, prescriptionstFormBtn, prescriptionPrioritiesFormBtn, specializations);
        HorizontalLayout center = new HorizontalLayout();
        center.addComponents(navBar, content);
        mainLayout.addComponent(center);
        setContent(mainLayout);
    }
}