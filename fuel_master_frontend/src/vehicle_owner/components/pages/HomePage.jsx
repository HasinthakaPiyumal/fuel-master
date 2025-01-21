import React from "react";
import { useNavigate } from "react-router-dom";



const HomePage = () => {
  const navigate = useNavigate();

  return (
    <div className="homepage">
      <header className="homepage-header">
        <img src={fuelLogo} alt="Fuel Master Logo" className="logo" />
        <h1>Welcome to the Fuel Master!</h1>
      </header>
      <p className="homepage-subtitle">
        Manage your fuel quota seamlessly by registering your vehicle today!
      </p>
      <ol className="features-list">
        <li>Create a new account or login to an existing account.</li>
        <li>Fill in your vehicle details to register.</li>
        <li>Check the status of your registration anytime.</li>
        <li>Receive your unique QR code for fuel quota management.</li>
      </ol>
      <div className="button-group">
        <button className="login-button" onClick={() => navigate("/login")}>
          Login
        </button>
        <button
          className="create-account-button"
          onClick={() => navigate("/register")}
        >
          Create Account
        </button>
      </div>
      <img
        src={fuelStationIllustration}
        alt="Fuel Station"
        className="homepage-image"
      />
    </div>
  );
};

export default HomePage;
