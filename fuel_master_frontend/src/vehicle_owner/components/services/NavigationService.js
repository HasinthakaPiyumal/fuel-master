import { useNavigate } from 'react-router-dom';

const NavigationService = () => {
  const navigate = useNavigate();

  const goToHomePage = () => navigate('/');
  const goToUserLoginPage = () => navigate('/login');
  const goToUserRegistrationPage = () => navigate('/register');
  const goToVehicleRegistrationPage = () => navigate('/vehicle-register');

  return {
    goToHomePage,
    goToUserLoginPage,
    goToUserRegistrationPage,
    goToVehicleRegistrationPage,
  };
};

export default NavigationService;