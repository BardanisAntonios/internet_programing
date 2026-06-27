package com.medical.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.medical.db.UserDAO;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();
        
        if ("addDoctor".equals(action)) {
            dao.registerDoctor(
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("specialty")
            );
        }
        response.sendRedirect("admin/dashboard.jsp");
    }
}