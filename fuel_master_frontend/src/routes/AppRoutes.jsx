import { Routes, Route } from "react-router-dom";
import SignUpPage from "@/pages/user/sign_up/sign_up";
import LoginPage from "@/pages/user/login/login_page";
import PhoneNumber from "@/pages/user/phone_number/phone_number";
import VehicleRegistration from "@/pages/user/dashboard/dashboard";

import VerifyOtpPage from "../pages/user/otp/otp_verification";
import AppLayout from "@/layouts/AppLayout";
import NotFound from "@/pages/error/NotFound";
import VehiclePage from "@/pages/user/vehicle/vehicle_page";
import ProtectedRoute from "./UserProtectedRoutes";

const AppRoutes = () => {
  return (
    <AppLayout>
      <Routes>
        <Route path="login" element={<LoginPage />} />
        <Route path="signup" element={<SignUpPage />} />
        <Route path="otp" element={<VerifyOtpPage />} />
        <Route path="phone" element={<PhoneNumber />} />
        <Route path="vehicle" element={<VehicleRegistration />} />
        <Route element={<ProtectedRoute />}>
          <Route path="dashboard" element={<VehiclePage />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </AppLayout>
  );
};

export default AppRoutes;
