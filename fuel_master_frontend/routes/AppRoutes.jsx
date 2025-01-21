import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/user/home/home_page';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      {/* Add more routes here as needed */}
    </Routes>
  );
};

export default AppRoutes;