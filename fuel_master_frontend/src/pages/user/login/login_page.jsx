import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';
import { Input } from '@/components/ui/input';
import { EyeIcon, EyeOffIcon } from 'lucide-react';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";

const loginSchema = z.object({
    phone: z.string()
        .min(10, "Phone number must be at least 10 digits")
        .max(15, "Phone number must not exceed 15 digits")
        .regex(/^\+?[0-9]+$/, "Invalid phone number format"),
    password: z.string()
        .min(8, "Password must be at least 8 characters")
        .max(50, "Password must not exceed 50 characters"),
});

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false);
    
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm({
        resolver: zodResolver(loginSchema),
        defaultValues: {
            phone: '',
            password: '',
        },
    });

    const onSubmit = async (data) => {
        try {
            console.log('Form data:', data);
            // I want to add API calls here
        } catch (error) {
            console.error('Login error:', error);
        }
    };

    return (
        <React.Fragment>
            <div className="flex items-center justify-center">
                
                <div className="lg:w-1/2 hidden lg:flex justify-center">
                    <FuelStationAnimation />
                </div>

               
                <div className="lg:w-1/2 w-full max-w-md mx-auto p-6">
                    <div className="bg-white shadow-lg rounded-lg p-8">
                        <h1 className="text-3xl font-bold text-orange-600 text-center mb-6">
                            Welcome back!
                        </h1>
                        <form className="space-y-4" onSubmit={handleSubmit(onSubmit)}>
                            <div className="space-y-2">
                                <label htmlFor="phone" className="text-sm font-medium text-gray-700">
                                    Phone Number
                                </label>
                                <Input 
                                    id="phone" 
                                    type="tel" 
                                    placeholder="Phone number" 
                                    className={`h-12 ${errors.phone ? 'border-red-500' : ''}`}
                                    {...register("phone")}
                                    disabled={isSubmitting}
                                />
                                {errors.phone && (
                                    <p className="text-sm text-red-500 mt-1">{errors.phone.message}</p>
                                )}
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
                                        className={`h-12 pr-10 ${errors.password ? 'border-red-500' : ''}`}
                                        {...register("password")}
                                        disabled={isSubmitting}
                                    />
                                    <button
                                        type="button"
                                        onClick={() => setShowPassword(!showPassword)}
                                        className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                                    >
                                        {showPassword ? <EyeOffIcon className="h-5 w-5" /> : <EyeIcon className="h-5 w-5" />}
                                    </button>
                                </div>
                                {errors.password && (
                                    <p className="text-sm text-red-500 mt-1">{errors.password.message}</p>
                                )}
                            </div>

                            <Button 
                                type="submit" 
                                className="w-full h-12 text-white bg-[#FF5533] hover:bg-[#FF5533]/90"
                                disabled={isSubmitting}
                            >
                                {isSubmitting ? 'Logging in...' : 'Log in'}
                            </Button>
                        </form>

                        <p className="text-center text-gray-600 mt-4">
                            Don't have an account?{" "}
                            <Link to="/signup" className="text-[#FF5533] hover:underline">
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