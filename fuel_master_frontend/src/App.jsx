import { BrowserRouter, Route, Routes } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import AdminRoutes from "./routes/AdminRoutes";
import { Toaster } from "./components/ui/toaster";
import HomePage from "./pages/user/home/home_page";
import AppLayout from "./layouts/AppLayout";
import NotFound from "./pages/error/NotFound";
import AdminLogin from "./pages/admin/login/page";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ProtectedRoute from "./routes/AdminProtectedRoutes";


const queryClient = new QueryClient()
const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route index element={<AppLayout><HomePage /></AppLayout>} />
          <Route path="/*" element={<AppRoutes />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/admin/*" element={<AdminRoutes />} />
          </Route>
          <Route path="/admin-login" element={<AdminLogin />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
        <Toaster />
      </BrowserRouter>
    </QueryClientProvider>
  );
};

export default App;
