import { zodResolver } from "@hookform/resolvers/zod";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { useNavigate } from "react-router-dom";
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
import axios from "axios";

const phoneFormSchema = z.object({
  phoneNumber: z
    .string()
    .min(10, "Phone number must be at least 10 digits")
    .max(15, "Phone number must not exceed 15 digits")
    .regex(/^\+?[1-9]\d{1,14}$/, "Please enter a valid phone number"),
});

export default function PhoneNumber() {
  const navigate = useNavigate();

  const form = useForm({
    resolver: zodResolver(phoneFormSchema),
    defaultValues: {
      phoneNumber: "",
    },
  });

  async function onSubmit(values) {
    try {
      if (!token) {
        throw new Error("No authentication token found");
      }

      const response = await axios.put(
        "http://localhost:8080/api/v1/user/change-phone",
        { phoneNumber: values.phoneNumber },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        console.log("Phone number updated successfully:", response.data);
        navigate(-1);
      }
    } catch (error) {
      console.error("Error updating phone number:", error);
      if (error.response?.status === 401) {
        localStorage.removeItem("token");
        navigate("/login");
      }
    }
  }

  return (
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
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
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
