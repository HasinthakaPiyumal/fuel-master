import { BrowserRouter, Route, Routes } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import AdminRoutes from "./routes/AdminRoutes";
import { Toaster } from "./components/ui/toaster";
import HomePage from "./pages/user/home/home_page";
import AppLayout from "./layouts/AppLayout";
import NotFound from "./pages/error/NotFound";
import AdminLogin from "./pages/admin/login/page";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<AppLayout><HomePage /></AppLayout>} />
        <Route path="/*" element={<AppRoutes />} />
        <Route path="/admin/*" element={<AdminRoutes />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
      <Toaster />
    </BrowserRouter>
  );
};

export default App;
