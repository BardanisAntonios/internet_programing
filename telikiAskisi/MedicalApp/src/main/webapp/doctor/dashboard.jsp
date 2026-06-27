<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.medical.model.*" %>
<%@ page import="com.medical.db.AppointmentDAO" %>
<%@ page import="com.medical.utils.SecurityUtils" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || ! (user instanceof Doctor)) { response.sendRedirect("../index.jsp"); return; }
    
    AppointmentDAO dao = new AppointmentDAO();
    List<Appointment> mySchedule = dao.getDoctorSchedule(user.getId());
%>

<!DOCTYPE html>
<html lang="el">
<head>
    <meta charset="UTF-8">
    <title>Doctor Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
          rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <style>
        body { background-color: #f8fcfd; }
        .navbar { background-color: #009688; }
        .card-header { font-weight: bold; background: white; border-bottom: 2px solid #f1f1f1; }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark mb-4">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🏥 Medical Doctor</a>
            <div class="d-flex text-white align-items-center">
                <span class="me-3">Dr. <%= SecurityUtils.sanitize(user.getSurname()) %></span>
                <a href="../AuthServlet?action=logout" class="btn btn-outline-light btn-sm">Αποσύνδεση</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <!-- Φόρμα Προσθήκης Διαθεσιμότητας -->
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-header text-success">📅 Νέα Διαθεσιμότητα</div>
                    <div class="card-body">
                        <form action="../ActionServlet" method="post">
                            <input type="hidden" name="action" value="addAvailability">
                            <div class="mb-3">
                                <label class="form-label">Ημερομηνία</label>
                                <input type="date" name="date" class="form-control" required>
                            </div>
                            <div class="mb-4">
                                <label class="form-label">Ώρα</label>
                                <input type="time" name="time" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-success w-100">➕ Προσθήκη</button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Πίνακας Προγράμματος -->
            <div class="col-md-8">
                <div class="card h-100">
                    <div class="card-header text-primary">📋 Το Πρόγραμμά μου</div>
                    <div class="card-body">
                        <% if (mySchedule.isEmpty()) { %>
                            <div class="alert alert-info">Δεν έχετε καταχωρήσει πρόγραμμα ακόμα.</div>
                        <% } else { %>
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>Ημερομηνία & Ώρα</th>
                                            <th>Ασθενής</th>
                                            <th>Κατάσταση</th>
                                            <th>Ενέργειες</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Appointment app : mySchedule) { %>
                                        <tr>
                                            <td><%= app.getDateTime() %></td>
                                            <td>
                                                <span class="fw-bold"><%= SecurityUtils.sanitize(app.getDoctorName()) %></span>
                                            </td>
                                            <td>
                                                <span class="badge bg-secondary"><%= app.getStatus() %></span>
                                            </td>
                                            <td>
                                                <% if (!"CANCELLED".equals(app.getStatus())) { %>
                                                <form action="../ActionServlet" method="post" onsubmit="return confirm('Ακύρωση ραντεβού;');">
                                                    <input type="hidden" name="action" value="cancel">
                                                    <input type="hidden" name="appId" value="<%= app.getId() %>">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm">Ακύρωση</button>
                                                </form>
                                                <% } %>
                                            </td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>