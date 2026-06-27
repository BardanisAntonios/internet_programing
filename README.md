# Medical Appointment Management System

This repository contains a **3-tier** web application for managing medical appointments, developed as a semester project at the University of Piraeus (Department of Informatics) for the course "Programming on the Internet & the World Wide Web" (2024–2025). [file:10][file:11]

The system is implemented in Java EE (Servlets, JSP), follows the Model–View–Controller (MVC) pattern and uses a MySQL relational database. It supports different user roles (Admin, Doctor, Patient), secure authentication and business rules for appointment booking and cancellation. [file:10][file:11]

## Main features

- 3-tier MVC architecture (Model, View, Controller). [file:10][file:11]  
- Role-based access: **Admin**, **Doctor**, **Patient**. [file:10][file:11]  
- Secure login with SHA-256 password hashing (HashUtils). [file:10][file:11]  
- Management of doctors and patients (1-to-1 relation with `users` table, AMKA, specialty). [file:10][file:11]  
- Creation of available appointment slots by doctors (status: AVAILABLE). [file:10][file:11]  
- Booking and cancellation of appointments by patients, with business rule: cancellation allowed only if the appointment date is more than 3 days away. [file:10][file:11]  
- Separate dashboards for each role (admin, doctor, patient). [file:10][file:11]  
- Screenshots of the login page, dashboards and database tables (hashed passwords). [file:10][file:11]

## Technology stack

- Java EE (Servlets, JSP) for the application logic and views. [file:10][file:11]  
- HTML/CSS for the user interface. [file:10][file:11]  
- JDBC for database connectivity. [file:10][file:11]  
- MySQL (`medical_db`) as the relational database. [file:10][file:11]

## Architecture overview

### Model (Data Layer)

- Entity classes: `User`, `Patient`, `Doctor`, `Appointment` representing the core domain objects. [file:10][file:11]  
- DAO classes: `UserDAO`, `AppointmentDAO` executing SQL queries and encapsulating persistence logic. [file:10][file:11]  
- `DBConnection` class that manages the JDBC connection to the `medical_db` database. [file:10][file:11]  
- `HashUtils` utility for SHA-256 password hashing. [file:10][file:11]

### View (Presentation Layer)

- JSP pages with HTML/CSS, organized in folders:
  - `admin/` – admin dashboard and doctor management pages. [file:10][file:11]  
  - `doctor/` – doctor dashboard, availability and schedule views. [file:10][file:11]  
  - `patient/` – patient dashboard, available appointments and history. [file:10][file:11]  
- Dynamic rendering of data using JSP scriptlets. [file:10][file:11]

### Controller (Processing Layer)

- Servlets handling HTTP requests (GET/POST), invoking Model methods and forwarding to JSP views: [file:10][file:11]
  - `AuthServlet` – login/logout and session handling.  
  - `RegisterServlet` – patient registration and validation of username/AMKA.  
  - `AdminServlet` – admin actions such as adding doctors.  
  - `ActionServlet` – booking and cancelling appointments.

## Database design

Database name: `medical_db`. [file:10][file:11]

Main tables:

- `users` – login credentials and role information. Passwords are stored as SHA-256 hashes, not plain text. [file:10][file:11]  
- `patients` / `doctors` – additional details per user (AMKA, specialty) linked with 1-to-1 relationships to `users`. [file:10][file:11]  
- `appointments` – links doctors and patients, with fields for date/time and `status` (`AVAILABLE`, `BOOKED`, `CANCELLED`). [file:10][file:11]

## Role-based functionality

### Admin

- Logs in with admin credentials. [file:10][file:11]  
- Registers new doctors by specifying username, password (hashed automatically) and specialty. [file:10][file:11]  
- Uses the `admin/dashboard.jsp` view to manage doctor data. [file:10][file:11]

### Doctor

- Defines new available appointment slots by selecting date and time. [file:10][file:11]  
- Views their schedule via `getDoctorSchedule` in the DAO, including patient names and appointment status. [file:10][file:11]  
- Cancels appointments when needed. [file:10][file:11]

### Patient

- Registers via `RegisterServlet`, with validation for unique username and AMKA. [file:10][file:11]  
- Sees a list of available appointments from all doctors (`status = 'AVAILABLE'`) and can book them through `ActionServlet` (`book` action). [file:10][file:11]  
- Has a personal history page and can cancel future appointments, respecting the 3-day rule enforced in `AppointmentDAO`. [file:10][file:11]

## Screenshots

The report includes example screenshots demonstrating: [file:10][file:11]

- Login page and initial view.  
- New patient registration form.  
- Admin dashboard – adding doctors.  
- Doctor dashboard – availability management and schedule list.  
- Patient dashboard – available appointments and history.  
- Database view – `users` table with hashed passwords.

## Academic context

This project was implemented by Antonis Mpardanis (Student ID: P21110) as the final semester assignment for the course "Programming on the Internet & the World Wide Web" at the University of Piraeus, during the academic year 2024–2025. [file:10][file:11]

---

Θες να σου φτιάξω και ελληνική έκδοση του README (με τίτλους/ενότητες στα ελληνικά) για να την βάλεις και σαν `REPORT.md`;  

<user_response_autocomplete>
Ναι θέλω πλήρη ελληνικό README
Όχι αρκεί το αγγλικό README
Θέλω μόνο μια σύντομη ελληνική περίληψη
</user_response_autocomplete>
