import { useState } from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { EyeIcon, EyeOffIcon } from "lucide-react";
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
import { showToast } from "@/hooks/use-toast";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import apiService from "@/services/api.service";

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
  const [showPasswords, setShowPasswords] = useState(false);
  const [loading, setLoading] = useState(false);

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
    setLoading(true);
    try {
      const data = {
        firstName: values.firstName,
        lastName: values.lastName,
        phone: values.phoneNumber,
        nic: values.nic,
        password: values.password,
      };
      const response = await apiService.post("/v1/user/save", data);

      if (response.status === 200) {
        showToast.success("Account created successfully!");
      } else {
        showToast.error(
          response.data.message || "Something went wrong. Please try again."
        );
      }

      form.reset({
        firstName: "",
        lastName: "",
        phoneNumber: "",
        nic: "",
        password: "",
        confirmPassword: "",
        terms: false,
      });

      window.location.href = "/login";
    } catch (error) {
      showToast.error(
        error.response.data.message || "Something went wrong. Please try again."
      );
    } finally {
      setLoading(false);
    }
  }

  const togglePasswordVisibility = () => {
    setShowPasswords((prev) => !prev);
  };

  return (
    <div>
      <div className="container">
        <div className="flex items-center justify-between">
          <div className="lg:w-1/2 hidden lg:flex justify-center">
            <FuelStationAnimation />
          </div>

          <div className="w-full lg:w-5/12 pb-8">
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

                    <div className="grid grid-cols-2 gap-4">
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
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                      <FormField
                        control={form.control}
                        name="password"
                        render={({ field }) => (
                          <FormItem>
                            <FormLabel>Password</FormLabel>
                            <FormControl>
                              <div className="relative">
                                <Input
                                  type={showPasswords ? "text" : "password"}
                                  placeholder="Enter your password"
                                  className="pr-10"
                                  {...field}
                                />
                                <button
                                  type="button"
                                  onClick={togglePasswordVisibility}
                                  className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                                >
                                  {showPasswords ? (
                                    <EyeOffIcon className="h-5 w-5" />
                                  ) : (
                                    <EyeIcon className="h-5 w-5" />
                                  )}
                                </button>
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
                                  type={showPasswords ? "text" : "password"}
                                  placeholder="Confirm password"
                                  className="pr-10"
                                  {...field}
                                />
                                <button
                                  type="button"
                                  onClick={togglePasswordVisibility}
                                  className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                                >
                                  {showPasswords ? (
                                    <EyeOffIcon className="h-5 w-5" />
                                  ) : (
                                    <EyeIcon className="h-5 w-5" />
                                  )}
                                </button>
                              </div>
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                    </div>

                    <FormField
                      control={form.control}
                      name="terms"
                      render={({ field }) => (
                        <FormItem>
                          <div className="flex items-center">
                            <FormControl>
                              <input
                                type="checkbox"
                                id="terms-checkbox"
                                {...field}
                                className="h-4 w-4 text-orange-600 focus:ring-orange-500 border-gray-300 rounded"
                              />
                            </FormControl>
                            <label
                              htmlFor="terms-checkbox"
                              className="ml-2 block text-sm text-gray-700 cursor-pointer"
                              onClick={() =>
                                form.setValue("terms", !field.value)
                              }
                            >
                              I agree to{" "}
                              <a
                                href="#"
                                className="text-orange-600 underline"
                                onClick={(e) => {
                                  e.stopPropagation();
                                }}
                              >
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
                        className="w-full"
                        loading={loading}
                      >
                        Sign Up
                      </Button>
                    </div>

                    <p className="mt-4 text-sm text-center text-gray-600">
                      Already have an account?{" "}
                      <a href="/login" className="text-orange-600 underline">
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
