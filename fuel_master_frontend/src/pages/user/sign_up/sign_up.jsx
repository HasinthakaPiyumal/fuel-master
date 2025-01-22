import React from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { toast } from "@/hooks/use-toast";
import { Card, CardContent, CardHeader } from "@/components/ui/card";

// Define the form validation schema
const signUpFormSchema = z
  .object({
    email: z.string().email("Please enter a valid email address"),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters")
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/,
        "Password must contain at least one uppercase letter, one lowercase letter, and one number"
      ),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ["confirmPassword"],
  });

export default function SignUpPage() {
  // Initialize form with Zod validation
  const form = useForm({
    resolver: zodResolver(signUpFormSchema),
    defaultValues: {
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  // Handle form submission
  async function onSubmit(values) {
    try {
      // Add your signup logic here
      console.log(values);
      toast({
        title: "Success",
        description: "Account created successfully!",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Error",
        description: error.message || "Something went wrong. Please try again.",
      });
    }
  }

  return (
    <div className="min-h-screen">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
          {/* Left Section with Illustration */}
          <div className="w-full lg:w-5/12 order-2 lg:order-1 animate-fade-in">
            <FuelStationAnimation />
          </div>

          {/* Right Section with Form */}
          <div className="w-full lg:w-5/12 order-1 lg:order-2 animate-fade-in">
            <Card>
              <CardHeader>
                <h2 className="text-2xl font-bold text-orange-600">
                  Unlock Your Potential
                </h2>
              </CardHeader>
              <CardContent>
                <Form {...form}>
                  <form
                    onSubmit={form.handleSubmit(onSubmit)}
                    className="space-y-6"
                  >
                    <FormField
                      control={form.control}
                      name="email"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Email</FormLabel>
                          <FormControl>
                            <Input placeholder="Enter your email" {...field} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={form.control}
                      name="password"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Password</FormLabel>
                          <FormControl>
                            <Input
                              type="password"
                              placeholder="Enter your password"
                              {...field}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={form.control}
                      name="confirmPassword"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Confirm Passwords</FormLabel>
                          <FormControl>
                            <Input
                              type="password"
                              placeholder="Confirm your password"
                              {...field}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

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
                      <Button
                        type="submit"
                        className="w-full bg-orange-600 text-white py-2 px-4 rounded-md shadow-sm hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-orange-500"
                      >
                        Sign Up
                      </Button>
                    </div>

                    {/* Sign In Link */}
                    <p className="mt-4 text-sm text-center text-gray-600">
                      Already have an account?{" "}
                      <a href="/signin" className="text-orange-600 underline">
                        Sign In
                      </a>
                    </p>
                  </form>
                </Form>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}
