package com.medical.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.medical.db.UserDAO;
import com.medical.utils.SecurityUtils;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String amka = request.getParameter("amka");
        
        // --- SECURITY: Input Validation ---
        if (!SecurityUtils.isStrongPassword(password)) {
            response.sendRedirect("index.jsp?error=WeakPassword");
            return;
        }
        
        if (amka == null || !amka.matches("\\d{11}")) {
            response.sendRedirect("index.jsp?error=InvalidAmka");
            return;
        }

        UserDAO dao = new UserDAO();
        boolean success = dao.registerPatient(username, password, name, surname, amka);
        
        if (success) {
            response.sendRedirect("index.jsp?msg=RegisteredSuccessfully");
        } else {
            response.sendRedirect("index.jsp?error=RegistrationFailed");
        }
    }
}