import React from "react";
import UserLogin from "../forms/UserLogin";

const UserLoginPage = () => {
  const handleLogin = (phoneNumber, password) => {
    console.log("Logging in with:", phoneNumber, password);
  };

  return (
    <div className="user-login-page">
      <h1>Welcome back!</h1>
      <UserLogin onLogin={handleLogin} />
    </div>
  );
};

export default UserLoginPage;