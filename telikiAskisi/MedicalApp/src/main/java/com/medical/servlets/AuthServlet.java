package com.medical.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.medical.db.UserDAO;
import com.medical.model.*;

@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        
        // --- ΑΣΦΑΛΕΙΑ: Αν το ZAP στείλει null, σταματάμε ΕΔΩ ---
        if (u == null || p == null) {
            response.sendRedirect("index.jsp?error=InvalidInput");
            return; 
        }

        // Αυθεντικοποίηση μέσω DAO
        UserDAO dao = new UserDAO();
        User user = dao.authenticate(u, p); 
        
        if (user != null) {
            // Δημιουργία Session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // Redirect ανάλογα με τον ρόλο
            if (user instanceof Admin) response.sendRedirect("admin/dashboard.jsp");
            else if (user instanceof Doctor) response.sendRedirect("doctor/dashboard.jsp");
            else response.sendRedirect("patient/dashboard.jsp");
        } else {
            response.sendRedirect("index.jsp?error=InvalidCredentials");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logout λειτουργία
        if ("logout".equals(request.getParameter("action"))) {
            request.getSession().invalidate();
            response.sendRedirect("index.jsp");
        }
    }
}