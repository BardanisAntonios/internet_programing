<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="el">
<head>
    <meta charset="UTF-8">
    <title>MedicalApp - Είσοδος</title>
    <!-- Bootstrap CSS με Integrity Hash (για να περνάει το ZAP scan) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
          crossorigin="anonymous">
    <style>
        body {
            background: linear-gradient(135deg, #74ebd5 0%, #ACB6E5 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card { border-radius: 15px; box-shadow: 0 10px 20px rgba(0,0,0,0.2); }
    </style>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card p-4">
                <div class="text-center mb-4">
                    <h2 class="fw-bold text-primary">🏥 Medical App</h2>
                    <p class="text-muted">Ασφαλές Σύστημα Διαχείρισης Ραντεβού</p>
                </div>

                <!-- Καρτέλες (Tabs) για Εναλλαγή Login / Register -->
                <ul class="nav nav-tabs mb-3" id="myTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="login-tab" data-bs-toggle="tab" data-bs-target="#login" type="button">Σύνδεση</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="register-tab" data-bs-toggle="tab" data-bs-target="#register" type="button">Εγγραφή Ασθενή</button>
                    </li>
                </ul>

                <div class="tab-content">
                    
                    <!-- Φόρμα Login -->
                    <div class="tab-pane fade show active" id="login">
                        <!-- Διαχείριση Μηνυμάτων Λάθους -->
                        <% if ("InvalidCredentials".equals(request.getParameter("error"))) { %>
                            <div class="alert alert-danger p-2 text-center">Λάθος όνομα χρήστη ή κωδικός!</div>
                        <% } else if ("InvalidInput".equals(request.getParameter("error"))) { %>
                            <div class="alert alert-warning p-2 text-center">Μη έγκυρα δεδομένα εισόδου.</div>
                        <% } else if ("RegisteredSuccessfully".equals(request.getParameter("msg"))) { %>
                            <div class="alert alert-success p-2 text-center">Επιτυχής εγγραφή! Συνδεθείτε.</div>
                        <% } %>

                        <form action="AuthServlet" method="post">
                            <div class="mb-3">
                                <label class="form-label">Username</label>
                                <input type="text" name="username" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100 py-2">Είσοδος</button>
                        </form>
                    </div>

                    <!-- Φόρμα Register -->
                    <div class="tab-pane fade" id="register">
                        <% if ("WeakPassword".equals(request.getParameter("error"))) { %>
                            <div class="alert alert-warning p-2 small">Ο κωδικός πρέπει να έχει 8+ χαρακτήρες, γράμματα & αριθμούς.</div>
                        <% } %>
                        
                        <form action="RegisterServlet" method="post">
                            <div class="mb-2">
                                <input type="text" name="username" class="form-control" placeholder="Username" required>
                            </div>
                            <div class="mb-2">
                                <input type="password" name="password" class="form-control" placeholder="Password (Ισχυρός)" required>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <input type="text" name="name" class="form-control mb-2" placeholder="Όνομα" required>
                                </div>
                                <div class="col">
                                    <input type="text" name="surname" class="form-control mb-2" placeholder="Επίθετο" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <input type="text" name="amka" class="form-control" placeholder="AMKA (11 ψηφία)" required maxlength="11">
                            </div>
                            <button type="submit" class="btn btn-success w-100">Δημιουργία Λογαριασμού</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="text-center mt-3 text-muted small">&copy; 2024-2025 Medical App Project</div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
        crossorigin="anonymous"></script>
</body>
</html>