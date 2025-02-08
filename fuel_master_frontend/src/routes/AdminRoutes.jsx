import { Routes, Route, Navigate } from 'react-router-dom';
import AdminLayout from '@/layouts/AdminLayout';
import Dashboard from '@/pages/admin/dashboard/page';
import AddEmployee from '@/pages/admin/employee/add-employee/page';
import ListEmployee from '@/pages/admin/employee/list-employee/page';
import AddVehicleTypes from '@/pages/admin/vehicle-types/add-vehicle-types/page';
import ListVehicleTypes from '@/pages/admin/vehicle-types/list-vehicle-types/page';
import NewStation from '@/pages/admin/station/new-station/page';
import StationList from '@/pages/admin/station/station-list/page';
import StationMasterAdd from '@/pages/admin/station/station-master-add/page';
import Users from '@/pages/admin/users/page';

const AdminRoutes = () => {
  return (
    <AdminLayout>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/employee-add" element={<AddEmployee />} />
        <Route path="/employee-list" element={<ListEmployee />} />
        <Route path="/vehicle-type-add" element={<AddVehicleTypes />} />
        <Route path="/vehicle-type-list" element={<ListVehicleTypes />} />
        <Route path="/station-add" element={<NewStation />} />
        <Route path="/station-list" element={<StationList />} />
        <Route path="/station-master-add" element={<StationMasterAdd />} />
        <Route path="/user-list" element={<Users />} />
        <Route path="*" element={<Navigate to="/admin/dashboard" />} />
      </Routes>
    </AdminLayout>

  );
};

export default AdminRoutes; 