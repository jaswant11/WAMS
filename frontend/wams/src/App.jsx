import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './routes/ProtectedRoute';

// Login
import Login from './pages/login/login';

// Admin
import AdminPanel from './pages/admin/AdminPanel';

// Employee
import EmployeeDashboard from './pages/employee/EmployeeDashboard';
import SubmitAvailability from './pages/employee/SubmitAvailability';
import RequestTimeOff from './pages/employee/RequestTimeOff';
import SubmitWorklog from './pages/employee/SubmitWorklog';
import ViewWorklogs from './pages/employee/ViewWorklogs';
import FatigueScore from './pages/employee/FatigueScore';
import EmployeeWellbeing from './pages/employee/EmployeeWellbeing'; 
import Training from './pages/employee/Training';// Assuming this is the correct import path

// Manager
import ManagerDashboard from './pages/manager/ManagerDashboard';
import ShiftTemplateManager from './pages/manager/ShiftTemplateManager';
import AssignShift from './pages/manager/AssignShift';
import ApproveTimeOff from './pages/manager/ApproveTimeOff';
import ViewAvailability from './pages/manager/ViewAvailability';
import ViewWorklogsManager from './pages/manager/ViewWorklogsManager';
import ViewEmployeesWithFatigue from './pages/manager/ViewEmployeesWithFatigue';


function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Login */}
          <Route path="/" element={<Login />} />

          {/* Admin */}
          <Route
            path="/admin/dashboard"
            element={
              <ProtectedRoute allowedRoles={['ADMIN']}>
                <AdminPanel />
              </ProtectedRoute>
            }
          />

          {/* Employee */}
          <Route
            path="/employee/dashboard"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <EmployeeDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/employee-Wellbeing"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <EmployeeWellbeing />
              </ProtectedRoute>
            }
            
          />
          <Route
            path="/employee/training"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <Training />
              </ProtectedRoute>
            }
            
          />
          <Route
            path="/employee/submit-availability"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <SubmitAvailability />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/request-timeoff"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <RequestTimeOff />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/submit-worklog"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <SubmitWorklog />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/view-worklogs"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <ViewWorklogs />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/fatigue"
            element={
              <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                <FatigueScore />
              </ProtectedRoute>
            }
          />

          {/* Manager */}
          <Route
            path="/manager/dashboard"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ManagerDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/create-template"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ShiftTemplateManager />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/assign-shift"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <AssignShift />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/approve-timeoff"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ApproveTimeOff />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/view-availability"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ViewAvailability />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/view-worklogs"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ViewWorklogsManager />
              </ProtectedRoute>
            }
          />
          <Route
            path="/manager/view-employees"
            element={
              <ProtectedRoute allowedRoles={['MANAGER']}>
                <ViewEmployeesWithFatigue />
              </ProtectedRoute>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
