package com.medical.model;

public class Doctor extends User {
    private String specialty;

    /**
     * Constructor που καλείται όταν τραβάμε δεδομένα από τη βάση.
     * 
     * @param id Το ID από τον πίνακα users
     * @param username Το username
     * @param name Το όνομα
     * @param surname Το επώνυμο
     * @param specialty Η ειδικότητα από τον πίνακα doctors
     */
    public Doctor(int id, String username, String name, String surname, String specialty) {
        super(id, username, name, surname); // Κλήση στον constructor της User
        this.specialty = specialty;
    }

    // Getters και Setters
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    // Debugging
    @Override
    public String toString() {
        return "Dr. " + this.surname + " (" + this.specialty + ")";
    }
}
