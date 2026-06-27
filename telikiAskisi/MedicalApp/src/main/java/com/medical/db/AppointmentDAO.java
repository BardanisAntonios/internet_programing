package com.medical.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.medical.model.Appointment;

public class AppointmentDAO {

    // Προσθήκη Διαθεσιμότητας από Γιατρό
    public void addAvailability(int doctorId, String dateTime) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO appointments (doctor_id, date_time, status) VALUES (?, ?, 'AVAILABLE')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, doctorId);
            ps.setString(2, dateTime);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Κλείσιμο Ραντεβού από Ασθενή
    public boolean bookAppointment(int appointmentId, int patientId) {
        try (Connection con = DBConnection.getConnection()) {
            // Ελέγχουμε αν το ραντεβού είναι ακόμα διαθέσιμο (concurrency check)
            String sql = "UPDATE appointments SET patient_id = ?, status = 'BOOKED' WHERE id = ? AND status = 'AVAILABLE'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Ακύρωση Ραντεβού (με έλεγχο 3 ημερών)
    public boolean cancelAppointment(int appointmentId) {
        try (Connection con = DBConnection.getConnection()) {
            String checkSql = "SELECT date_time FROM appointments WHERE id = ?";
            PreparedStatement psCheck = con.prepareStatement(checkSql);
            psCheck.setInt(1, appointmentId);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                Timestamp appDate = rs.getTimestamp("date_time");
                long threeDaysInMillis = 3L * 24 * 60 * 60 * 1000;
                
                // Αν η ημερομηνία είναι 3 μέρες μετά από τώρα, επιτρέπουμε ακύρωση
                if (appDate.getTime() > (System.currentTimeMillis() + threeDaysInMillis)) {
                    String updateSql = "UPDATE appointments SET status = 'AVAILABLE', patient_id = NULL WHERE id = ?";
                    PreparedStatement psUpd = con.prepareStatement(updateSql);
                    psUpd.setInt(1, appointmentId);
                    psUpd.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    // Λίστα Διαθέσιμων Ραντεβού (για Ασθενείς)
    public List<Appointment> getAvailableAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.date_time, u.name, u.surname, d.specialty " +
                     "FROM appointments a " +
                     "JOIN doctors d ON a.doctor_id = d.user_id " +
                     "JOIN users u ON d.user_id = u.id " +
                     "WHERE a.status = 'AVAILABLE' AND a.date_time > NOW() ORDER BY a.date_time ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment app = new Appointment(
                    rs.getInt("id"), 
                    "Dr. " + rs.getString("surname") + " (" + rs.getString("specialty") + ")", 
                    rs.getString("date_time"), 
                    "AVAILABLE"
                );
                list.add(app);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;    
    }

    // Ιστορικό Ασθενή
    public List<Appointment> getAppointments(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.date_time, a.status, u.surname, d.specialty " +
                     "FROM appointments a " +
                     "JOIN doctors d ON a.doctor_id = d.user_id " +
                     "JOIN users u ON d.user_id = u.id " +
                     "WHERE a.patient_id = ? ORDER BY a.date_time DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setInt(1, patientId);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                list.add(new Appointment(rs.getInt("id"), "Dr. " + rs.getString("surname"), rs.getString("date_time"), rs.getString("status")));
             }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // Πρόγραμμα Γιατρού
    public List<Appointment> getDoctorSchedule(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.date_time, a.status, u.surname, u.name " +
                     "FROM appointments a " +
                     "LEFT JOIN patients p ON a.patient_id = p.user_id " +
                     "LEFT JOIN users u ON p.user_id = u.id " +
                     "WHERE a.doctor_id = ? ORDER BY a.date_time DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setInt(1, doctorId);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                String patName = (rs.getString("surname") != null) ? rs.getString("surname") : "Διαθέσιμο";
                list.add(new Appointment(rs.getInt("id"), patName, rs.getString("date_time"), rs.getString("status")));
             }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}