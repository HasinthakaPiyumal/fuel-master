import { zodResolver } from "@hookform/resolvers/zod";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Navigate, useNavigate } from "react-router-dom";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useEffect } from "react";
import apiService from "@/services/api.service";
import { useMutation, useQuery } from "@tanstack/react-query";
import { alert } from "@/lib/alert";
import Loading from "@/components/loading";

const phoneFormSchema = z.object({
  phoneNumber: z
    .string()
    .min(10, "Phone number must be at least 10 digits")
    .max(15, "Phone number must not exceed 15 digits")
    .regex(/^[0]\d{9}$/, "Please enter a valid phone number"),
});

export default function PhoneNumber() {
  const navigate = useNavigate();

  const { data: allData, isLoading } = useQuery({
    queryKey: ["user"],
    queryFn: async () => {
      const response = await apiService.get("/user/authenticate");
      return response.data.data;
    },
    retry: false,
  });

  const user = allData?.user;

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
    }
  }, [navigate]);

  const form = useForm({
    resolver: zodResolver(phoneFormSchema),
    defaultValues: {
      phoneNumber: "",
    },
  });

  const { mutate: updatePhoneNumber, isPending } = useMutation({
    mutationFn: (values) => apiService.post("/user/change-phone", {
      phoneNumber: values.phoneNumber,
    }),
    onSuccess: () => {
      navigate("/otp");
    },
    onError: (error) => {
      alert.error(error?.response?.data?.message || "Failed to update phone number");
    },
  });

  if (!isLoading && !user) {
    return <Navigate to="/login" />;
  }

  if (!isLoading && user.verified) {
    return <Navigate to="/dashboard" />;
  }

  return isLoading ? <Loading /> : (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
        <div className="lg:w-1/2">
          <FuelStationAnimation />
        </div>

        <div
          className="w-[561px] h-[431px] bg-white p-8 rounded-lg shadow-lg flex flex-col justify-center"
          style={{ textAlign: "center" }}
        >
          <div className="text-center mb-6">
            <h1 className="text-2xl font-bold text-[#FF5733]">
              Change Phone No.
            </h1>
          </div>

          <Form {...form}>
            <form onSubmit={form.handleSubmit(updatePhoneNumber)} className="space-y-6">
              <FormField
                control={form.control}
                name="phoneNumber"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Phone Number</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Phone number"
                        {...field}
                        className="rounded-md"
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button
                type="submit"
                className="w-full bg-[#FF5733] hover:bg-[#ff5733]/90 text-white py-2 px-4 rounded-md"
                loading={isPending}
              >
                Send Verification Code
              </Button>
            </form>
          </Form>

          <Button
            variant="link"
            className="text-[#FF5733] hover:text-[#ff5733]/90 mt-4"
            onClick={() => navigate(-1)}
          >
            Go Back
          </Button>
        </div>
      </div>
    </div>
  );
}
