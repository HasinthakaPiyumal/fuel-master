import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/user/home/home_page";
import SignUpPage from "@/pages/user/sign_up/sign_up";
import LoginPage from "@/pages/user/login/login_page";
import PhoneNumber from "@/pages/user/phone_number/phone_number";

import VerifyOtpPage from '../pages/user/otp/otp_verification';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/otp" element={<VerifyOtpPage />} />
      <Route path="/phone" element={<PhoneNumber />} />
    </Routes>
  );
};

export default AppRoutes;
