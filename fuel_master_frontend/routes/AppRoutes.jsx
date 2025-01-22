import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/user/home/home_page';
import LoginPage from '../pages/user/login/login_page';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      {/* Add more routes here as needed */}
    </Routes>
  );
};

export default AppRoutes;