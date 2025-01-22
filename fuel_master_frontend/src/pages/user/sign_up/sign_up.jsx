import React, { useState } from "react";
import Lottie from "lottie-react";
import fuelStationAnimation from "@/assets/animations/fuel-station-animation.json";
import "@fortawesome/fontawesome-free/css/all.min.css";

const SignUp = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    nic: "",
    password: "",
    confirmPassword: "",
    terms: false,
  });

  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const validate = () => {
    const newErrors = {};

    // Validate first name and last name
    if (!formData.firstName.trim())
      newErrors.firstName = "First name is required.";
    if (!formData.lastName.trim())
      newErrors.lastName = "Last name is required.";

    // Validate phone number
    if (!/^\d{10}$/.test(formData.phoneNumber))
      newErrors.phoneNumber = "Phone number must be 10 digits.";

    // Validate NIC (you can adjust the regex to fit your specific NIC format)
    if (!formData.nic.trim() || formData.nic.length < 10)
      newErrors.nic = "NIC must be at least 10 characters.";

    // Validate password
    if (
      !/^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,}$/.test(
        formData.password
      )
    )
      newErrors.password =
        "Password must be at least 8 characters, with one uppercase, one number, and one special character.";

    // Validate confirm password
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = "Passwords do not match.";

    // Validate terms and conditions
    if (!formData.terms)
      newErrors.terms = "You must agree to the Terms and Conditions.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      alert("Form submitted successfully!");
      // Add your form submission logic here
    }
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-transparent">
      {/* Animation Section */}
      <div className="w-1/2 h-full flex items-center justify-center bg-transparent">
        <Lottie
          animationData={fuelStationAnimation}
          className="w-full max-w-md"
        />
      </div>

      {/* Form Section */}
      <div className="w-1/2 h-full flex flex-col justify-center p-8 bg-white shadow-md rounded-lg max-w-lg">
        <h2 className="text-2xl font-bold text-orange-500 mb-6 text-center">
          Unlock Your Potential
        </h2>
        <form className="space-y-4" onSubmit={handleSubmit}>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label
                htmlFor="firstName"
                className="block text-sm font-medium text-gray-700"
              >
                First Name
              </label>
              <input
                id="firstName"
                name="firstName"
                placeholder="First name"
                type="text"
                value={formData.firstName}
                onChange={handleChange}
                className="p-2 border rounded w-full mt-1"
              />
              {errors.firstName && (
                <p className="text-red-500 text-sm mt-1">{errors.firstName}</p>
              )}
            </div>
            <div>
              <label
                htmlFor="lastName"
                className="block text-sm font-medium text-gray-700"
              >
                Last Name
              </label>
              <input
                id="lastName"
                name="lastName"
                placeholder="Last name"
                type="text"
                value={formData.lastName}
                onChange={handleChange}
                className="p-2 border rounded w-full mt-1"
              />
              {errors.lastName && (
                <p className="text-red-500 text-sm mt-1">{errors.lastName}</p>
              )}
            </div>
          </div>
          <div>
            <label
              htmlFor="phoneNumber"
              className="block text-sm font-medium text-gray-700"
            >
              Phone Number
            </label>
            <input
              id="phoneNumber"
              name="phoneNumber"
              placeholder="Phone number"
              type="text"
              value={formData.phoneNumber}
              onChange={handleChange}
              className="p-2 border rounded w-full mt-1"
            />
            {errors.phoneNumber && (
              <p className="text-red-500 text-sm mt-1">{errors.phoneNumber}</p>
            )}
          </div>
          <div>
            <label
              htmlFor="nic"
              className="block text-sm font-medium text-gray-700"
            >
              NIC
            </label>
            <input
              id="nic"
              name="nic"
              placeholder="NIC"
              type="text"
              value={formData.nic}
              onChange={handleChange}
              className="p-2 border rounded w-full mt-1"
            />
            {errors.nic && (
              <p className="text-red-500 text-sm mt-1">{errors.nic}</p>
            )}
          </div>
          <div className="relative">
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              Password
            </label>
            <input
              id="password"
              name="password"
              placeholder="Password"
              type={showPassword ? "text" : "password"}
              value={formData.password}
              onChange={handleChange}
              className="p-2 border rounded w-full mt-1"
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-2 top-9 text-gray-500"
            >
              <i
                className={`fa-solid ${
                  showPassword ? "fa-eye" : "fa-eye-slash"
                }`}
              ></i>
            </button>
            {errors.password && (
              <p className="text-red-500 text-sm mt-1">{errors.password}</p>
            )}
          </div>
          <div className="relative">
            <label
              htmlFor="confirmPassword"
              className="block text-sm font-medium text-gray-700"
            >
              Confirm Password
            </label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              placeholder="Confirm password"
              type={showConfirmPassword ? "text" : "password"}
              value={formData.confirmPassword}
              onChange={handleChange}
              className="p-2 border rounded w-full mt-1"
            />
            <button
              type="button"
              onClick={() => setShowConfirmPassword(!showConfirmPassword)}
              className="absolute right-2 top-9 text-gray-500"
            >
              <i
                className={`fa-solid ${
                  showConfirmPassword ? "fa-eye" : "fa-eye-slash"
                }`}
              ></i>
            </button>
            {errors.confirmPassword && (
              <p className="text-red-500 text-sm mt-1">
                {errors.confirmPassword}
              </p>
            )}
          </div>
          <div className="flex items-center mt-4">
            <input
              id="terms"
              name="terms"
              type="checkbox"
              checked={formData.terms}
              onChange={handleChange}
              className="mr-2"
            />
            <label htmlFor="terms" className="text-sm text-gray-700">
              I agree to{" "}
              <a href="#" className="text-orange-500">
                Terms and Conditions
              </a>
            </label>
            {errors.terms && (
              <p className="text-red-500 text-sm mt-1 ml-2">{errors.terms}</p>
            )}
          </div>
          <button className="w-full bg-orange-500 text-white py-2 rounded mt-4">
            Sign Up
          </button>
        </form>
        <p className="text-center mt-3">
          Already have an account?{" "}
          <a href="#" className="text-orange-500">
            Sign In
          </a>
        </p>
      </div>
    </div>
  );
};

export default SignUp;
