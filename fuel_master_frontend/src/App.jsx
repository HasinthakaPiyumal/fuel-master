import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';
import AdminRoutes from './routes/AdminRoutes';
import AppLayout from './layouts/AppLayout';
import AdminLayout from './layouts/AdminLayout';

const App = () => {
  return (
    <BrowserRouter>
      <AppLayout>
        <AppRoutes />
      </AppLayout>
      <AdminLayout>
        <AdminRoutes />
      </AdminLayout>
    </BrowserRouter>
  );
};

export default App;
