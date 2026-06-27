package com.medical.servlets;

import java.io.IOException;

import com.medical.db.AppointmentDAO;
import com.medical.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        AppointmentDAO appDao = new AppointmentDAO();
        User user = (User) request.getSession().getAttribute("user");

        if ("addAvailability".equals(action)) {
             String date = request.getParameter("date"); // "2024-12-01"
             String time = request.getParameter("time"); // "10:00:00"
             appDao.addAvailability(user.getId(), date + " " + time);
             response.sendRedirect("doctor/dashboard.jsp");
             
        } else if ("book".equals(action)) {
             int appId = Integer.parseInt(request.getParameter("appId"));
             appDao.bookAppointment(appId, user.getId());
             response.sendRedirect("patient/dashboard.jsp");
             
        } else if ("cancel".equals(action)) {
             int appId = Integer.parseInt(request.getParameter("appId"));
             boolean success = appDao.cancelAppointment(appId);
             String referer = request.getHeader("Referer"); // Επιστροφή στην ίδια σελίδα
             response.sendRedirect(referer + (success ? "?msg=Cancelled" : "?msg=CannotCancel_TooLate"));
        }
    }
}