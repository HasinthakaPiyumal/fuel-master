import React from "react";
import FuelStationAnimation from '@/components/animation/FuelStationAnimation';

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { toast } from "react-hot-toast";

import { Button } from "../../../components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../../components/ui/form";
import {
  InputOTP,
  InputOTPGroup,
  InputOTPSlot,
} from "../../../components/ui/input-otp";

const FormSchema = z.object({
  pin: z.string().min(6, {
    message: "Your one-time password must be 6 characters.",
  }),
});

export default function VerifyOtpPage() {
  const form = useForm({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      pin: "",
    },
  });

  function onSubmit(data) {
    try {
      const verifyOTP = async () => {
        const response = await fetch('/api/verify-otp', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ otp: data.pin }),
        });

        if (!response.ok) {
          throw new Error('OTP verification failed');
        }

        toast.success('OTP verified successfully!');
      };

      verifyOTP();
    } catch (error) {
      toast.error('Failed to verify OTP. Please try again.');
      console.error('OTP verification error:', error);
    }
  }

  return (
    <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
          {/* Left Section with Illustration */}
          <div className="lg:w-1/2">
            <FuelStationAnimation />
          </div>

        {/* Right Section: OTP Form */}
        <div className="bg-white shadow-lg rounded-lg p-8 md:p-12">
          <h1 className="text-2xl font-bold text-orange-600 mb-4 text-center">
            Verify OTP
          </h1>
          <p className="text-gray-600 text-center mb-6">
            We've sent a 6-digit verification code to <strong>076 321 5389</strong>.
            Please enter the OTP below. <br />
            <span className="text-sm">
              If this isn't your number?{" "}
              <a href="#" className="text-orange-600 underline">
                Change
              </a>
            </span>
          </p>

          {/* OTP Input Form */}
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(onSubmit)}
              className="space-y-6 w-full"
            >
              <FormField
                control={form.control}
                name="pin"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>One-Time Password</FormLabel>
                    <FormControl>
                      <InputOTP maxLength={6} {...field}>
                        <InputOTPGroup>
                          <InputOTPSlot index={0} />
                          <InputOTPSlot index={1} />
                          <InputOTPSlot index={2} />
                          <InputOTPSlot index={3} />
                          <InputOTPSlot index={4} />
                          <InputOTPSlot index={5} />
                        </InputOTPGroup>
                      </InputOTP>
                    </FormControl>
                    <FormDescription>
                      Please enter the one-time password sent to your phone.
                    </FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type="submit" className="w-full bg-orange-600">
                Verify
              </Button>
            </form>
          </Form>

          {/* Resend Link */}
          <p className="text-center text-sm text-gray-600 mt-4">
            Didn't receive it?{" "}
            <a href="#" className="text-orange-600 underline">
              Resend
            </a>
          </p>
        </div>
      </div>

      {/* Footer */}
      <footer className="absolute bottom-0 w-full bg-orange-600 text-white py-2 text-center text-sm">
        Copyright © 2025 GROUP 4
      </footer>
    </div>
  );
}
