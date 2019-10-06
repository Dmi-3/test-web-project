package com.haulmont.testtask.model;

public class Doctor
{
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Specialization specialization;

    public Doctor(Long id, String firstName, String lastName, String patronymic, Specialization specialization)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.specialization = specialization;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }

    public Specialization getSpecialization()
    {
        return specialization;
    }

    public void setSpecialization(Specialization specialization)
    {
        this.specialization = specialization;
    }
}
