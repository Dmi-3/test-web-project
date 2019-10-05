package com.haulmont.testtask.model;

public class Patient
{
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phone;

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

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

}
