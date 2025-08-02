# Worker Availability Management System (WAMS)

## 📌 Overview

WAMS is an all-in-one workforce management system designed to streamline employee scheduling, availability tracking, fatigue monitoring, time-off management, and communication between managers and employees. It serves the purpose of improving shift assignments, reducing scheduling conflicts, ensuring employee well-being, and increasing organizational productivity.

This system includes dedicated dashboards for Admin, Manager, and Employee roles, each equipped with functionalities tailored to their responsibilities.

---

## 🔍 Core Features & Business Logic

### 1. **User Authentication & Role-Based Access**
- **What it does**: Allows login for three roles: Admin, Manager, Employee.
- **Business Logic**: Verifies credentials, sets session (local or temporary storage), and redirects users based on roles.
- **Why it's needed**: To ensure role-based security and data isolation between employees and managers/admins.

---

### 2. **Admin Panel**
- **What it does**:
  - View all users (Admin, Managers, Employees).
  - Edit/delete users.
  - View fatigue scores for employees.
- **Business Logic**: Fetches user data from DB, links to related tables (e.g., Employee details).
- **Why it's needed**: Centralized management and oversight of all system users, ensuring control and updates can be made when needed.

---

### 3. **Employee Dashboard**
- **What it does**:
  - Submit weekly availability.
  - Request time off.
  - Submit daily worklogs.
  - View fatigue score.
- **Business Logic**:
  - Availability submission updates DB with selected days & time ranges.
  - Worklogs submitted include hours worked and qualitative fatigue indicators.
  - Fatigue scores are computed server-side using worklog history.
- **Why it's needed**:
  - Helps employees communicate availability.
  - Enables self-reporting of fatigue for proactive scheduling.
  - Allows employees to take charge of their work-life balance.

---

### 4. **Manager Dashboard**
- **What it does**:
  - Create shift templates with staffing requirements.
  - Assign employees to shifts.
  - View employee availability and fatigue scores.
  - Approve/decline time-off requests.
- **Business Logic**:
  - Shift templates generate recurring shifts over a date range and assign staff based on availability + fatigue.
  - Manager reviews employee health (fatigue) before assigning.
- **Why it's needed**:
  - Balances operational requirements with employee well-being.
  - Automates shift creation for recurring weekly needs.
  - Ensures managers schedule healthy, available staff.

---

### 5. **Shift Scheduling Engine**
- **What it does**:
  - Generates shifts from templates.
  - Automatically assigns employees based on match with availability & fatigue.
- **Business Logic**:
  - Each shift includes staffing requirements (number of employees).
  - Assignments are only made if the employee is available and not over-fatigued.
- **Why it's needed**:
  - Reduces manual effort.
  - Ensures fair distribution and avoids overwork.

---

### 6. **Fatigue Monitoring System**
- **What it does**:
  - Calculates fatigue score from worklogs (hours worked, stress level, sleep quality).
  - Provides fatigue insights to managers/admins.
- **Business Logic**:
  - Algorithm evaluates historical work data.
  - Flags employees with high fatigue for scheduling caution.
- **Why it's needed**:
  - Prevents burnout.
  - Promotes health-oriented scheduling.
  - Ensures long-term productivity and morale.

---

### 7. **Time-Off Request System**
- **What it does**:
  - Allows employees to submit leave requests with reason and date range.
  - Managers/admins can approve or deny.
- **Business Logic**:
  - Requests stored in DB, status updated by manager.
  - Shift engine excludes approved time-off periods during assignment.
- **Why it's needed**:
  - Formal time-off tracking.
  - Prevents double-booking and ensures transparency.

---

### 8. **Chat System (Real-Time Messaging)**
- **What it does**:
  - Allows managers and employees to communicate within the system.
- **Business Logic**:
  - Messages stored in DB or socket-based service.
  - Enables real-time or stored communication history.
- **Why it's needed**:
  - Reduces communication gaps.
  - Enables quick conflict resolution or updates.

---

### 9. **Calendar View**
- **What it does**:
  - Displays all shifts assigned to the logged-in employee.
- **Business Logic**:
  - Fetches shifts from DB based on user ID.
  - Visual UI calendar integration using date ranges.
- **Why it's needed**:
  - Makes shift planning transparent and clear for employees.
  - Encourages punctuality and accountability.

---

## 📂 Project Structure
WAMS/
├── backend/
│ ├── controller/
│ ├── model/
│ ├── service/
│ ├── repository/
│ └── application.properties
├── frontend/
│ ├── components/
│ ├── pages/
│ ├── services/
│ └── App.jsx

## 📈 Future Scope

- AI-assisted shift recommendations.
- Employee wellness trend analytics.
- Mobile application integration.
- Real-time alerts for fatigue or scheduling issues.

---

## 🧠 Why WAMS?

Traditional scheduling systems ignore employee health and lead to overworked staff. WAMS was created to:
- Align operational needs with employee availability.
- Prevent burnout and fatigue.
- Empower managers with intelligent scheduling tools.
- Promote a healthy, transparent work environment.

---

## 🛠️ Tech Stack

- **Frontend**: React.js
- **Backend**: Spring Boot (Java)
- **Database**: PostgreSQL
- **Authentication**: JWT / Session-based (configurable)
- **Tools**: Postman, Git, Thunder Client, VSCode

---

## 📜 License

This project is for academic and educational purposes only. For commercial usage, please contact the authors.
