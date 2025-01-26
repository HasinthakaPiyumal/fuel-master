import { Routes, Route } from 'react-router-dom';
import AdminLayout from '@/layouts/AdminLayout';

const AdminRoutes = () => {
  return (
    <AdminLayout>
      <Routes>
        <Route path="/" element={<div className='font-bold text-2xl'>admin</div>} />
      </Routes>
    </AdminLayout>
  );
};

export default AdminRoutes; 