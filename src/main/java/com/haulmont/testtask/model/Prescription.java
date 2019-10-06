package com.haulmont.testtask.model;

import java.util.Date;

public class Prescription
{
    private Long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private Date creatingDate;
    private PrescriptionPriority prescriptionPriority;

    public Prescription(Long id, String description, Patient patient, Doctor doctor, Date creatingDate,
                        PrescriptionPriority prescriptionPriority)
    {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creatingDate = creatingDate;
        this.prescriptionPriority = prescriptionPriority;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public Doctor getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }

    public Date getCreatingDate()
    {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate)
    {
        this.creatingDate = creatingDate;
    }

    public PrescriptionPriority getPrescriptionPriority()
    {
        return prescriptionPriority;
    }

    public void setPrescriptionPriority(PrescriptionPriority prescriptionPriority)
    {
        this.prescriptionPriority = prescriptionPriority;
    }
}
