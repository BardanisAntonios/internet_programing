package com.medical.db;

import java.sql.*;
import com.medical.model.*;
import com.medical.utils.SecurityUtils;

public class UserDAO {

    // Έλεγχος Login
    public User authenticate(String username, String password) {
        String hashedPassword = SecurityUtils.hashPassword(password);
        
        // Χρήση PreparedStatement για αποφυγή SQL Injection
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                
                if ("ADMIN".equals(role)) return new Admin(id, username, name, surname);
                if ("DOCTOR".equals(role)) return new Doctor(id, username, name, surname, "");
                if ("PATIENT".equals(role)) {
                    // Ανάκτηση και του ΑΜΚΑ
                    String sqlPat = "SELECT amka FROM patients WHERE user_id=?";
                    PreparedStatement ps2 = con.prepareStatement(sqlPat);
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    String amka = rs2.next() ? rs2.getString("amka") : "";
                    return new Patient(id, username, name, surname, amka);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // Εγγραφή Ασθενή
    public boolean registerPatient(String username, String password, String name, String surname, String amka) {
        try (Connection con = DBConnection.getConnection()) {
            String hashedPassword = SecurityUtils.hashPassword(password);
            
            String sqlUser = "INSERT INTO users (username, password, name, surname, role) VALUES (?, ?, ?, ?, 'PATIENT')";
            PreparedStatement ps = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                String sqlPat = "INSERT INTO patients (user_id, amka) VALUES (?, ?)";
                PreparedStatement ps2 = con.prepareStatement(sqlPat);
                ps2.setInt(1, userId);
                ps2.setString(2, amka);
                ps2.executeUpdate();
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    // Εγγραφή Γιατρού (από Admin)
    public boolean registerDoctor(String username, String password, String name, String surname, String specialty) {
        try (Connection con = DBConnection.getConnection()) {
            String hashedPassword = SecurityUtils.hashPassword(password);
            
            String sqlUser = "INSERT INTO users (username, password, name, surname, role) VALUES (?, ?, ?, ?, 'DOCTOR')";
            PreparedStatement ps = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                String sqlDoc = "INSERT INTO doctors (user_id, specialty) VALUES (?, ?)";
                PreparedStatement ps2 = con.prepareStatement(sqlDoc);
                ps2.setInt(1, userId);
                ps2.setString(2, specialty);
                ps2.executeUpdate();
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}