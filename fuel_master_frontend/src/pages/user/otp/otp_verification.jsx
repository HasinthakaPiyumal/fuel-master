import { useEffect } from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { Navigate, useNavigate } from "react-router-dom";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import apiService from "@/services/api.service";
import { InputOTP, InputOTPGroup, InputOTPSlot } from "@/components/ui/input-otp";
import { alert } from "@/lib/alert";
import { useMutation, useQuery } from "@tanstack/react-query";
import { Button } from "@/components/ui/button";
import { getUserPhone } from "@/services/user.service";
import Loading from "@/components/loading";

const OtpSchema = z.object({
  otp: z
    .string()
    .min(6, "OTP must be exactly 6 digits")
    .max(6, "OTP must be exactly 6 digits")
    .regex(/^[0-9]+$/, "OTP must contain only numbers"),
});

export default function VerifyOtpPage() {
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

  const { data: userPhone } = useQuery({
    queryKey: ["userPhone"],
    queryFn: getUserPhone,
  });

  const { mutate: verifyOtp, isPending: isVerifying } = useMutation({
    mutationFn: (data) => apiService.post("/verification/verify", { code: data.otp }),
    onSuccess: () => {
      alert.success("OTP verified successfully!");
      navigate("/dashboard");
    },
    onError: (error) => {
      alert.error(error.response.data?.message || "Verification failed");
    },
  });

  const { mutate: resendOtp } = useMutation({
    mutationFn: () => apiService.get("/verification/resend"),
    onSuccess: () => {
      alert.success("New OTP has been sent!");
      form.reset();
    },
    onError: (error) => {
      alert.error(error.response.data?.message || "Failed to resend OTP");
    },
  });


  const form = useForm({
    resolver: zodResolver(OtpSchema),
    defaultValues: {
      otp: "",
    },
  });

  const onSubmit = async (data) => {
    verifyOtp(data);
  };

  const handleResendOTP = async () => {
    resendOtp();
  };

  useEffect(() => {

    const sendInitialOTP = async () => {
      const token = localStorage.getItem("token");
      if (!token) navigate("/login");
    };

    sendInitialOTP();
  }, [navigate]);

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

        <div className="w-[561px] h-[502px] bg-white rounded-xl p-16 shadow-lg">
          <h1 className="text-[#F04A23] text-3xl font-semibold text-center">
            Verify OTP
          </h1>
          <p className="font-normal text-center mt-4 text-sm">
            We&apos;ve sent a 6-digit verification code to {userPhone}. Please enter
            the OTP below.
          </p>
          <p className="text-center mt-2 text-sm">
            If this isn&apos;t your number?{" "}
            <span
              className="text-[#F04A23] cursor-pointer hover:underline"
              onClick={() => navigate("/phone")}
            >
              Change
            </span>
          </p>

          <form onSubmit={form.handleSubmit(onSubmit)} className="mt-6">
            <label className="font-medium text-sm block text-center mb-8">
              Verification code
            </label>
            <div className="flex justify-center mb-4">
              <InputOTP
                maxLength={6}
                {...form.register("otp")}
              >
                <InputOTPGroup>
                  <InputOTPSlot index={0} />
                  <InputOTPSlot index={1} />
                  <InputOTPSlot index={2} />
                  <InputOTPSlot index={3} />
                  <InputOTPSlot index={4} />
                  <InputOTPSlot index={5} />
                </InputOTPGroup>
              </InputOTP>
            </div>
            {form.formState.errors.otp && (
              <p className="text-red-500 text-sm mt-2 text-center">
                {form.formState.errors.otp.message}
              </p>
            )}

            <Button
              type="submit"
              loading={isVerifying}
              className="w-full mt-4"
            >
              Verify
            </Button>
          </form>

          <p className="text-right text-sm mt-4 ">
            Didn&apos;t receive it?{" "}
            <span
              className="text-[#F04A23] cursor-pointer hover:underline"
              onClick={handleResendOTP}
            >
              Resend
            </span>
          </p>
        </div>
      </div>
    </div>
  );
}
