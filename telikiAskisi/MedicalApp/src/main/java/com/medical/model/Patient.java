package com.medical.model;

public class Patient extends User {
    private String amka;

    public Patient(int id, String username, String name, String surname, String amka) {
        super(id, username, name, surname);
        this.amka = amka;
    }
    public String getAmka() { return amka; }
}