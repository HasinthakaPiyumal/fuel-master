import React from "react";
import UserRegistration from "../forms/UserRegistration";

const UserRegistrationPage = () => {
  const handleRegister = (formData) => {
    console.log("Registering user with data:", formData);
  };

  return (
    <div className="user-registration-page">
      <h1>Unlock Your Potential</h1>
      <UserRegistration onRegister={handleRegister} />
    </div>
  );
};

export default UserRegistrationPage;