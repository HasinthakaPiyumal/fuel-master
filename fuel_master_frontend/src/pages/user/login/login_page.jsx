import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';
import { Input } from '@/components/ui/input';
import { EyeIcon, EyeOffIcon } from 'lucide-react';

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false)
    return (
        <React.Fragment>
            <div className="flex items-center justify-center" >
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
                        <form className="space-y-4">
                            <div className="space-y-2">
                                <label htmlFor="phone" className="text-sm font-medium text-gray-700">
                                    Phone Number
                                </label>
                                <Input id="phone" type="tel" placeholder="Phone number" className="h-12" />
                            </div>

                            <div className="space-y-2">
                                <label htmlFor="password" className="text-sm font-medium text-gray-700">
                                    Password
                                </label>
                                <div className="relative">
                                    <Input
                                        id="password"
                                        type={showPassword ? "text" : "password"}
                                        placeholder="Password"
                                        className="h-12 pr-10"
                                    />
                                    <button
                                        type="button"
                                        onClick={() => setShowPassword(!showPassword)}
                                        className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                                    >
                                        {showPassword ? <EyeOffIcon className="h-5 w-5" /> : <EyeIcon className="h-5 w-5" />}
                                    </button>
                                </div>
                            </div>

                            <Button type="submit" className="w-full h-12 text-white bg-[#FF5533] hover:bg-[#FF5533]/90">
                                Log in
                            </Button>
                        </form>

                        <p className="text-center text-gray-600">
                            Don't have an account?{" "}
                            <Link href="/signup" className="text-[#FF5533] hover:underline">
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
