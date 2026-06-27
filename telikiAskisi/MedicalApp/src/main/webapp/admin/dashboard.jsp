<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.medical.model.*" %>
<%@ page import="com.medical.utils.SecurityUtils" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || ! (user instanceof Admin)) { response.sendRedirect("../index.jsp"); return; }
%>

<!DOCTYPE html>
<html lang="el">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
          rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <style>
        body { background-color: #f0f2f5; }
        .navbar { background: linear-gradient(to right, #2c3e50, #4ca1af); }
        .card-header { background-color: white; border-bottom: 2px solid #eee; font-weight: bold; color: #2c3e50; }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark mb-5">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🛡️ Medical Admin</a>
            <div class="d-flex text-white align-items-center">
                <span class="me-3">
                    Διαχειριστής: <%= SecurityUtils.sanitize(user.getName()) %> <%= SecurityUtils.sanitize(user.getSurname()) %>
                </span>
                <a href="../AuthServlet?action=logout" class="btn btn-outline-light btn-sm">Αποσύνδεση</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-sm border-0">
                    <div class="card-header py-3">👨‍⚕️ Προσθήκη Νέου Ιατρού</div>
                    <div class="card-body p-4">
                        <form action="../AdminServlet" method="post">
                            <input type="hidden" name="action" value="addDoctor">
                            
                            <div class="row mb-3">
                                <div class="col">
                                    <label class="form-label text-muted small">Username</label>
                                    <input type="text" name="username" class="form-control" required>
                                </div>
                                <div class="col">
                                    <label class="form-label text-muted small">Password</label>
                                    <input type="text" name="password" class="form-control" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col">
                                    <label class="form-label text-muted small">Όνομα</label>
                                    <input type="text" name="name" class="form-control" required>
                                </div>
                                <div class="col">
                                    <label class="form-label text-muted small">Επίθετο</label>
                                    <input type="text" name="surname" class="form-control" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label text-muted small">Ειδικότητα</label>
                                <select name="specialty" class="form-select" required>
                                    <option value="" selected disabled>Επιλέξτε...</option>
                                    <option value="Καρδιολόγος">Καρδιολόγος</option>
                                    <option value="Παθολόγος">Παθολόγος</option>
                                    <option value="Οφθαλμίατρος">Οφθαλμίατρος</option>
                                    <option value="Ορθοπεδικός">Ορθοπεδικός</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-success w-100 fw-bold py-2">✅ Αποθήκευση</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>