# Medical Appointment Management System

This repository contains a **3-tier** web application for managing medical appointments, developed as a semester project at the University of Piraeus (Department of Informatics) for the course "Programming on the Internet & the World Wide Web" (2024–2025). 

The system is implemented in Java EE (Servlets, JSP), follows the Model–View–Controller (MVC) pattern and uses a MySQL relational database. It supports different user roles (Admin, Doctor, Patient), secure authentication and business rules for appointment booking and cancellation. 

## Main features

- 3-tier MVC architecture (Model, View, Controller).   
- Role-based access: **Admin**, **Doctor**, **Patient**.   
- Secure login with SHA-256 password hashing (HashUtils).   
- Management of doctors and patients (1-to-1 relation with `users` table, AMKA, specialty).   
- Creation of available appointment slots by doctors (status: AVAILABLE).   
- Booking and cancellation of appointments by patients, with business rule: cancellation allowed only if the appointment date is more than 3 days away.   
- Separate dashboards for each role (admin, doctor, patient).   
- Screenshots of the login page, dashboards and database tables (hashed passwords). 

## Technology stack

- Java EE (Servlets, JSP) for the application logic and views.   
- HTML/CSS for the user interface.   
- JDBC for database connectivity.   
- MySQL (`medical_db`) as the relational database. 

## Architecture overview

### Model (Data Layer)

- Entity classes: `User`, `Patient`, `Doctor`, `Appointment` representing the core domain objects.   
- DAO classes: `UserDAO`, `AppointmentDAO` executing SQL queries and encapsulating persistence logic.   
- `DBConnection` class that manages the JDBC connection to the `medical_db` database.   
- `HashUtils` utility for SHA-256 password hashing. 

### View (Presentation Layer)

- JSP pages with HTML/CSS, organized in folders:
  - `admin/` – admin dashboard and doctor management pages.   
  - `doctor/` – doctor dashboard, availability and schedule views.   
  - `patient/` – patient dashboard, available appointments and history.   
- Dynamic rendering of data using JSP scriptlets. 

### Controller (Processing Layer)

- Servlets handling HTTP requests (GET/POST), invoking Model methods and forwarding to JSP views: 
  - `AuthServlet` – login/logout and session handling.  
  - `RegisterServlet` – patient registration and validation of username/AMKA.  
  - `AdminServlet` – admin actions such as adding doctors.  
  - `ActionServlet` – booking and cancelling appointments.

## Database design

Database name: `medical_db`. 

Main tables:

- `users` – login credentials and role information. Passwords are stored as SHA-256 hashes, not plain text.   
- `patients` / `doctors` – additional details per user (AMKA, specialty) linked with 1-to-1 relationships to `users`.   
- `appointments` – links doctors and patients, with fields for date/time and `status` (`AVAILABLE`, `BOOKED`, `CANCELLED`). 

## Role-based functionality

### Admin

- Logs in with admin credentials.   
- Registers new doctors by specifying username, password (hashed automatically) and specialty.   
- Uses the `admin/dashboard.jsp` view to manage doctor data. 

### Doctor

- Defines new available appointment slots by selecting date and time.   
- Views their schedule via `getDoctorSchedule` in the DAO, including patient names and appointment status.   
- Cancels appointments when needed. 

### Patient

- Registers via `RegisterServlet`, with validation for unique username and AMKA.   
- Sees a list of available appointments from all doctors (`status = 'AVAILABLE'`) and can book them through `ActionServlet` (`book` action).   
- Has a personal history page and can cancel future appointments, respecting the 3-day rule enforced in `AppointmentDAO`. 

## Screenshots

The report includes example screenshots demonstrating: 

- Login page and initial view.  
- New patient registration form.  
- Admin dashboard – adding doctors.  
- Doctor dashboard – availability management and schedule list.  
- Patient dashboard – available appointments and history.  
- Database view – `users` table with hashed passwords.

## Academic context

This project was implemented by Antonis Mpardanis (Student ID: P21110) as the final semester assignment for the course "Programming on the Internet & the World Wide Web" at the University of Piraeus, during the academic year 2024–2025. 

---

Θες να σου φτιάξω και ελληνική έκδοση του README (με τίτλους/ενότητες στα ελληνικά) για να την βάλεις και σαν `REPORT.md`;  

<user_response_autocomplete>
Ναι θέλω πλήρη ελληνικό README
Όχι αρκεί το αγγλικό README
Θέλω μόνο μια σύντομη ελληνική περίληψη
</user_response_autocomplete>
