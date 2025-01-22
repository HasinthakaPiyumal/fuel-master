
import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/user/home/home_page';
import SignUpPage from '@/pages/user/sign_up';
import LoginPage from '@/pages/user/login/login_page';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignUpPage />} />
    </Routes>
  );
};

export default AppRoutes;
