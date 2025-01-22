import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/user/home/home_page';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
};

export default AppRoutes;