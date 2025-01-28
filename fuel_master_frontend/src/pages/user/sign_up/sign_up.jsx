import React, { useState } from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
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
import axios from "axios";

const signUpFormSchema = z
  .object({
    firstName: z.string().min(1, "First name is required"),
    lastName: z.string().min(1, "Last name is required"),
    phoneNumber: z.string().regex(/^\d{10}$/, "Phone number must be 10 digits"),
    nic: z.string().min(5, "NIC is required"),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters")
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/,
        "Password must contain at least one uppercase letter, one lowercase letter, and one number"
      ),
    confirmPassword: z.string(),
    terms: z.literal(true, {
      errorMap: () => ({
        message: "You must agree to the Terms and Conditions",
      }),
    }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ["confirmPassword"],
  });

export default function SignUpPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const form = useForm({
    resolver: zodResolver(signUpFormSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      phoneNumber: "",
      nic: "",
      password: "",
      confirmPassword: "",
      terms: false,
    },
  });

  async function onSubmit(values) {
    try {
      const response = await axios.post(
        "https://api-fuel-master-fbc37438737d.herokuapp.com/api/v1/user/save",
        {
          firstName: values.firstName,
          lastName: values.lastName,
          phone: values.phoneNumber,
          nic: values.nic,
          password: values.password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      toast({
        title: "Success",
        description: "Account created successfully!",
      });

      form.reset({
        firstName: "",
        lastName: "",
        phoneNumber: "",
        nic: "",
        password: "",
        confirmPassword: "",
        terms: false,
      });

      window.location.href = "/signin";
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Error",
        description:
          error.response?.data?.message ||
          "Something went wrong. Please try again.",
      });
    }
  }

  return (
    <div className="min-h-screen">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
          <div className="lg:w-1/2">
            <FuelStationAnimation />
          </div>

          <div className="w-full lg:w-5/12">
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
                    <div className="grid grid-cols-2 gap-4">
                      <FormField
                        control={form.control}
                        name="firstName"
                        render={({ field }) => (
                          <FormItem>
                            <FormLabel>First Name</FormLabel>
                            <FormControl>
                              <Input placeholder="First Name" {...field} />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                      <FormField
                        control={form.control}
                        name="lastName"
                        render={({ field }) => (
                          <FormItem>
                            <FormLabel>Last Name</FormLabel>
                            <FormControl>
                              <Input placeholder="Last Name" {...field} />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                    </div>

                    <FormField
                      control={form.control}
                      name="phoneNumber"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Phone Number</FormLabel>
                          <FormControl>
                            <Input placeholder="Phone Number" {...field} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={form.control}
                      name="nic"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>NIC</FormLabel>
                          <FormControl>
                            <Input placeholder="NIC" {...field} />
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
                            <div className="relative">
                              <Input
                                type={showPassword ? "text" : "password"}
                                placeholder="Enter your password"
                                {...field}
                              />
                              <FontAwesomeIcon
                                icon={showPassword ? faEyeSlash : faEye}
                                className="absolute right-3 top-2.5 cursor-pointer text-gray-500"
                                onClick={() => setShowPassword((prev) => !prev)}
                              />
                            </div>
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
                          <FormLabel>Confirm Password</FormLabel>
                          <FormControl>
                            <div className="relative">
                              <Input
                                type={showConfirmPassword ? "text" : "password"}
                                placeholder="Confirm your password"
                                {...field}
                              />
                              <FontAwesomeIcon
                                icon={showConfirmPassword ? faEyeSlash : faEye}
                                className="absolute right-3 top-2.5 cursor-pointer text-gray-500"
                                onClick={() =>
                                  setShowConfirmPassword((prev) => !prev)
                                }
                              />
                            </div>
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    {/* Terms and Conditions */}
                    <FormField
                      control={form.control}
                      name="terms"
                      render={({ field }) => (
                        <FormItem>
                          <div className="flex items-center">
                            <FormControl>
                              <input
                                type="checkbox"
                                {...field}
                                className="h-4 w-4 text-orange-600 focus:ring-orange-500 border-gray-300 rounded"
                              />
                            </FormControl>
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
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <div className="mt-6">
                      <Button
                        type="submit"
                        className="w-full bg-orange-600 text-white py-2 px-4 rounded-md shadow-sm hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-orange-500"
                      >
                        Sign Up
                      </Button>
                    </div>

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
