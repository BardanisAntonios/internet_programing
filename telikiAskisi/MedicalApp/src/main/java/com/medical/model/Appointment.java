package com.medical.model;

public class Appointment {
    private int id; // Προσθήκη του ID
    private String doctorName;
    private String dateTime;
    private String status;

    // Updated Constructor
    public Appointment(int id, String doctorName, String dateTime, String status) {
        this.id = id;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.status = status;
    }
    
    // Constructor συμβατότητας (αν χρησιμοποιείται αλλού χωρίς ID, π.χ. απλή προβολή)
    public Appointment(String doctorName, String dateTime, String status) {
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getId() { return id; } // Getter για το ID
    public String getDoctorName() { return doctorName; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    
    // Setters αν χρειάζονται...
    public void setId(int id) { this.id = id; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setStatus(String status) { this.status = status; }
}