package com.medical.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Κλάση για τη διαχείριση της σύνδεσης με τη βάση δεδομένων MySQL

public class DBConnection {
	

    private static final String URL = "jdbc:mysql://localhost:3306/medical_db";
    private static final String USER = "javauser";
    private static final String PASS = "1234";

    // Μέθοδος που επιστρέφει το Connection object
    public static Connection getConnection() throws Exception {
        // Φόρτωση του JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Δημιουργία και επιστροφή της σύνδεσης
        return DriverManager.getConnection(URL, USER, PASS);
    }
}