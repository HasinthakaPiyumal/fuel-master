import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';

const HomePage = () => {
    return (
        <React.Fragment>
            <div className="flex flex-col lg:flex-row items-center justify-between">
                <div className="lg:w-1/2 mb-8 lg:mb-0">
                    <h1 className="text-5xl font-bold text-[#FF5733] mb-6">
                        Welcome to the Fuel Master!
                    </h1>

                    <p className="text-2xl text-gray-700 mb-8">
                        Manage your fuel quota seamlessly by registering your vehicle today!
                    </p>

                    <div className="space-y-4 text-gray-600 mb-8">
                        <p>1. Create a new account or login to existing account.</p>
                        <p>2. Fill in your vehicle details to register.</p>
                        <p>3. Check the status of your registration anytime.</p>
                        <p>4. Receive your unique QR code for fuel quota management.</p>
                    </div>

                    <div className="flex gap-4">
                        <Link to="/register">
                            <Button size="lg" variant="outline">
                                Login
                            </Button>
                        </Link>
                        <Link to="/login">
                            <Button size="lg">
                                Create Account
                            </Button>
                        </Link>
                    </div>
                </div>

                <div className="lg:w-1/2">
                    <FuelStationAnimation />
                </div>
            </div>
        </React.Fragment>
    );
};

export default HomePage; 