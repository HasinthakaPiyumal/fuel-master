import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Button } from '@/components/ui/button';
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';
import { Input } from '@/components/ui/input';
import { EyeIcon, EyeOffIcon } from 'lucide-react';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { toast } from 'react-hot-toast';

// Login form validation 
const loginSchema = z.object({
    phone: z.string()
        .min(10, "Phone number must be 10 digits")
        .max(10, "Phone number must be 10 digits")
        .regex(/^0[0-9]{9}$/, "Phone number must start with 0 and be 10 digits"),
    password: z.string()
        .min(8, "Password must be at least 8 characters")
});

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    
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
            
            const config = {
                method: 'post',
                maxBodyLength: Infinity,
                url: 'https://api-fuel-master-fbc37438737d.herokuapp.com/api/v1/user/login', 
                headers: { 
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify({
                    phone: data.phone,
                    password: data.password
                })
            };

            const response = await axios.request(config);
            
            if (response.data) {
                
                const token = response.data.token;
                localStorage.setItem('token', token);
                
             
                if (response.data.user) {
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                }

                
                toast.success('Login successful!');

               
                console.log('Login successful:', response.data);

                
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

                
                navigate('/home');
            }
        } catch (error) {
            console.error('Login error:', error);
            
            
            if (error.response) {
                
                const errorMessage = error.response.data?.message || 'Invalid credentials';
                toast.error(errorMessage);
            } else if (error.request) {
               
                toast.error('Server not responding. Please try again later.');
            } else {
              
                toast.error('An error occurred. Please try again.');
            }
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