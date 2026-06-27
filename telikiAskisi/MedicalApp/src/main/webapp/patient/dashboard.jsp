<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.medical.model.*" %>
<%@ page import="com.medical.db.AppointmentDAO" %>
<!-- 1. Εισαγωγή της κλάσης ασφαλείας -->
<%@ page import="com.medical.utils.SecurityUtils" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !(user instanceof Patient)) {
        response.sendRedirect("../index.jsp");
        return;
    }
    Patient patient = (Patient) user;
    AppointmentDAO dao = new AppointmentDAO();
    List<Appointment> availableApps = dao.getAvailableAppointments();
    List<Appointment> myApps = dao.getAppointments(patient.getId());
    String msg = request.getParameter("msg");
%>

<!DOCTYPE html>
<html lang="el">
<head>
    <meta charset="UTF-8">
    <title>Καρτέλα Ασθενή</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
      rel="stylesheet" 
      integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
      crossorigin="anonymous">
    <style>
        body { background-color: #f8f9fa; }
        .navbar { box-shadow: 0 2px 4px rgba(0,0,0,.1); }
        .card { border: none; box-shadow: 0 4px 6px rgba(0,0,0,.05); margin-bottom: 20px; }
        .card-header { background-color: #fff; border-bottom: 2px solid #f1f1f1; font-weight: bold; color: #333; }
        .status-badge { font-size: 0.9em; padding: 5px 10px; border-radius: 20px; }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🏥 MedicalApp</a>
            <div class="d-flex text-white align-items-center">
                <span class="me-3">
                    <!-- 2. Χρήση του sanitize() στα στοιχεία του χρήστη -->
                    👤 <%= SecurityUtils.sanitize(user.getName()) %> <%= SecurityUtils.sanitize(user.getSurname()) %>
                    <small class="opacity-75">(AMKA: <%= SecurityUtils.sanitize(patient.getAmka()) %>)</small>
                </span>
                <a href="../AuthServlet?action=logout" class="btn btn-light btn-sm fw-bold text-primary">Αποσύνδεση</a>
            </div>
        </div>
    </nav>

    <div class="container">
        
        <% if ("Cancelled".equals(msg)) { %>
            <div class="alert alert-success alert-dismissible fade show">
                ✅ Το ραντεβού ακυρώθηκε επιτυχώς.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } else if ("CannotCancel_TooLate".equals(msg)) { %>
            <div class="alert alert-danger alert-dismissible fade show">
                ⚠️ Δεν μπορείτε να ακυρώσετε (λιγότερο από 3 ημέρες πριν).
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="row">
            <!-- Διαθέσιμα Ραντεβού -->
            <div class="col-lg-6">
                <div class="card h-100">
                    <div class="card-header text-primary">📅 Διαθέσιμα Ραντεβού</div>
                    <div class="card-body">
                        <% if (availableApps.isEmpty()) { %>
                            <div class="text-center text-muted py-4">Δεν υπάρχουν διαθέσιμα ραντεβού.</div>
                        <% } else { %>
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>Γιατρός</th>
                                            <th>Ημερομηνία</th>
                                            <th>Ενέργεια</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Appointment app : availableApps) { %>
                                        <tr>
                                            <td>
                                                <!-- 3. Χρήση του sanitize() στο όνομα γιατρού -->
                                                <div class="fw-bold"><%= SecurityUtils.sanitize(app.getDoctorName()) %></div>
                                            </td>
                                            <td><%= app.getDateTime() %></td>
                                            <td>
                                                <form action="../ActionServlet" method="post">
                                                    <input type="hidden" name="action" value="book">
                                                    <input type="hidden" name="appId" value="<%= app.getId() %>">
                                                    <button type="submit" class="btn btn-success btn-sm w-100">Κράτηση</button>
                                                </form>
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

            <!-- Ιστορικό -->
            <div class="col-lg-6">
                <div class="card h-100">
                    <div class="card-header text-success">🗂️ Το Ιστορικό μου</div>
                    <div class="card-body">
                        <% if (myApps.isEmpty()) { %>
                            <div class="text-center text-muted py-4">Δεν έχετε ιστορικό ραντεβού.</div>
                        <% } else { %>
                            <div class="table-responsive">
                                <table class="table table-striped align-middle">
                                    <thead>
                                        <tr>
                                            <th>Ημερομηνία</th>
                                            <th>Γιατρός</th>
                                            <th>Κατάσταση</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Appointment app : myApps) { %>
                                        <tr>
                                            <td class="small"><%= app.getDateTime() %></td>
                                            <!-- 4. Χρήση του sanitize() και εδώ -->
                                            <td class="small"><%= SecurityUtils.sanitize(app.getDoctorName()) %></td>
                                            <td>
                                                <% if ("BOOKED".equals(app.getStatus())) { %>
                                                    <span class="badge bg-primary">Ενεργό</span>
                                                <% } else if ("COMPLETED".equals(app.getStatus())) { %>
                                                    <span class="badge bg-success">Ολοκληρωμένο</span>
                                                <% } else if ("CANCELLED".equals(app.getStatus())) { %>
                                                    <span class="badge bg-danger">Ακυρωμένο</span>
                                                <% } else { %>
                                                    <span class="badge bg-secondary"><%= app.getStatus() %></span>
                                                <% } %>
                                            </td>
                                            <td>
                                                <% if ("BOOKED".equals(app.getStatus())) { %>
                                                <form action="../ActionServlet" method="post" onsubmit="return confirm('Είστε σίγουροι;');">
                                                    <input type="hidden" name="action" value="cancel">
                                                    <input type="hidden" name="appId" value="<%= app.getId() %>">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm" title="Ακύρωση">❌</button>
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
</body>
</html>