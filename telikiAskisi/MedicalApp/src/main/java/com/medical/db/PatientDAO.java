package com.medical.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.medical.model.*;

public class PatientDAO {

    // 1. Login Λειτουργία
    public Patient checkLogin(String username, String password) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT u.id, u.username, u.name, u.surname, p.amka " +
                         "FROM users u JOIN patients p ON u.id = p.user_id " +
                         "WHERE u.username = ? AND u.password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("amka")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 2. Λήψη Ιστορικού Ραντεβού
    public List<Appointment> getAppointments(int patientId) {
        List<Appointment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT u.surname as doc_surname, a.date_time, a.status " +
                         "FROM appointments a " +
                         "JOIN doctors d ON a.doctor_id = d.user_id " +
                         "JOIN users u ON d.user_id = u.id " +
                         "WHERE a.patient_id = ? ORDER BY a.date_time DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patientId);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                    "Dr. " + rs.getString("doc_surname"),
                    rs.getString("date_time"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}