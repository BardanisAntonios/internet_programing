package com.medical.model;

public class User {
    protected int id;
    protected String username;
    protected String name;
    protected String surname;

    public User(int id, String username, String name, String surname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
    }
    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
}