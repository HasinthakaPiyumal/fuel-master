import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';

const LoginPage = () => {
    return (
        <React.Fragment>
            <div className="flex items-center justify-center h-screen" >
                {/* Left Section with Animation */}
                <div className="lg:w-1/2 hidden lg:flex justify-center">
                    <FuelStationAnimation />
                </div>

                {/* Login Card Section */}
                <div className="lg:w-1/2 w-full max-w-md mx-auto p-6">
                    <div className="bg-white shadow-lg rounded-lg p-8">
                        <h1 className="text-3xl font-bold text-orange-600 text-center mb-6">
                            Welcome back!
                        </h1>
                        <form className="space-y-6">
                            {/* Phone Number Input */}
                            <div>
                                <label htmlFor="phone" className="text-sm font-medium text-gray-700">
                                    Phone Number
                                </label>
                                <input
                                    type="tel"
                                    id="phone"
                                    placeholder="Enter your phone number"
                                    className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-orange-500 focus:border-orange-500"
                                />
                            </div>

                            {/* Password Input */}
                            <div>
                                <label htmlFor="password" className="text-sm font-medium text-gray-700">
                                    Password
                                </label>
                                <div className="relative">
                                    <input
                                        type="password"
                                        id="password"
                                        placeholder="Enter your password"
                                        className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-orange-500 focus:border-orange-500"
                                    />
                                    <button
                                        type="button"
                                        className="absolute inset-y-0 right-3 flex items-center text-gray-500"
                                    >
                                        👁️
                                    </button>
                                </div>
                            </div>

                            {/* Login Button */}
                            <div className="text-center">
                                <Button className="w-full bg-orange-600 hover:bg-orange-700 text-white">
                                    Log in
                                </Button>
                            </div>
                        </form>

                        {/* Sign Up Link */}
                        <p className="text-center text-sm text-gray-600 mt-4">
                            Don’t have an account?{' '}
                            <Link to="/register" className="text-orange-600 font-medium hover:underline">
                                Sign Up
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </React.Fragment>
    );
};

export default LoginPage;
