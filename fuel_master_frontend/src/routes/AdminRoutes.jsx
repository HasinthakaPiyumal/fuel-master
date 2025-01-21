import { Routes, Route } from 'react-router-dom';

const AdminRoutes = () => {
  return (
    <Routes>
      <Route path="/admin" element={<div className='text-white font-bold text-2xl'>admin</div>} />
      {/* Add more routes here as needed */}
    </Routes>
  );
};

export default AdminRoutes; 