import React from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";

export default function SignUpPage() {
  return (
    <div className="min-h-screen bg-gradient-to-r from-pink-50 to-pink-100">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
          {/* Left Section with Illustration */}
          <div className="w-full lg:w-5/12 order-2 lg:order-1 animate-fade-in">
            <FuelStationAnimation />
          </div>

          {/* Right Section with Form - removed bg-white/80 and shadow */}
          <div className="w-full lg:w-5/12 order-1 lg:order-2 animate-fade-in">
            <h2 className="text-2xl font-bold text-orange-600 mb-6">
              Unlock Your Potential
            </h2>

            <form>
              {/* First Name and Last Name */}
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    First Name
                  </label>
                  <input
                    type="text"
                    placeholder="First name"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Last Name
                  </label>
                  <input
                    type="text"
                    placeholder="Last name"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  />
                </div>
              </div>

              {/* Phone Number */}
              <div className="mt-4">
                <label className="block text-sm font-medium text-gray-700">
                  Phone Number
                </label>
                <input
                  type="text"
                  placeholder="Phone number"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                />
              </div>

              {/* NIC */}
              <div className="mt-4">
                <label className="block text-sm font-medium text-gray-700">
                  NIC
                </label>
                <input
                  type="text"
                  placeholder="NIC"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                />
              </div>

              {/* Password and Confirm Password */}
              <div className="mt-4 grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Password
                  </label>
                  <input
                    type="password"
                    placeholder="Password"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Confirm Password
                  </label>
                  <input
                    type="password"
                    placeholder="Confirm Password"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  />
                </div>
              </div>

              {/* Terms and Conditions */}
              <div className="mt-4 flex items-center">
                <input
                  type="checkbox"
                  id="terms"
                  className="h-4 w-4 text-orange-600 focus:ring-orange-500 border-gray-300 rounded"
                />
                <label
                  htmlFor="terms"
                  className="ml-2 block text-sm text-gray-700"
                >
                  I agree to{" "}
                  <a href="#" className="text-orange-600 underline">
                    Terms and Conditions
                  </a>
                </label>
              </div>

              {/* Submit Button */}
              <div className="mt-6">
                <button
                  type="submit"
                  className="w-full bg-orange-600 text-white py-2 px-4 rounded-md shadow-sm hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-orange-500"
                >
                  Sign Up
                </button>
              </div>

              {/* Sign In Link */}
              <p className="mt-4 text-sm text-center text-gray-600">
                Already have an account?{" "}
                <a href="/signin" className="text-orange-600 underline">
                  Sign In
                </a>
              </p>
            </form>
          </div>
        </div>
      </div>
      <footer className="text-center mt-8 text-sm text-gray-500">
        Copyright © 2025 GROUP 4
      </footer>
    </div>
  );
}
