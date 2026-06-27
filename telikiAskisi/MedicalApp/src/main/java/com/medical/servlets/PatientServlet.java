package com.medical.servlets;

import java.io.IOException;
import java.util.List;

import com.medical.db.PatientDAO;
import com.medical.model.Appointment;
import com.medical.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Λειτουργία LOGIN
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        
        PatientDAO dao = new PatientDAO();
        Patient patient = dao.checkLogin(u, p);
        
        if (patient != null) {
            // Επιτυχής σύνδεση - Αποθήκευση στο Session
            HttpSession session = request.getSession();
            session.setAttribute("currentPatient", patient);
            
            // Φόρτωση Ραντεβού
            List<Appointment> apps = dao.getAppointments(patient.getId());
            session.setAttribute("appointmentsList", apps);
            
            // Μεταφορά στη σελίδα προφίλ
            response.sendRedirect("dashboard.jsp");
        } else {
            // Αποτυχία
            response.sendRedirect("index.html?error=1");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Για αποσύνδεση (Logout)
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("index.html");
        }
    }
}