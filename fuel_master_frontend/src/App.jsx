import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './vehicle_owner/components/pages/HomePage';
import UserLoginPage from './vehicle_owner/components/pages/UserLoginPage';
import UserRegistrationPage from './vehicle_owner/components/pages/UserRegistrationPage';
import VehicleRegistrationPage from './vehicle_owner/components/pages/VehicleRegistrationPage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<UserLoginPage />} />
        <Route path="/register" element={<UserRegistrationPage />} />
        <Route path="/vehicle-register" element={<VehicleRegistrationPage />} />
      </Routes>
    </Router>
  );
};

export default App;
