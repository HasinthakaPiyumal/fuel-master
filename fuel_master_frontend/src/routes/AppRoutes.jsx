import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/user/home/home_page";
import SignUp from "../pages/user/sign_up/sign_up";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/sign_up" element={<SignUp />} />
    </Routes>
  );
};

export default AppRoutes;
