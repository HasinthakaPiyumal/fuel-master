import React, { useState, useEffect } from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useNavigate } from "react-router-dom";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { showToast } from "@/hooks/use-toast";
import axios from "axios";

const OtpSchema = z.object({
  otp: z
    .string()
    .min(6, "OTP must be exactly 6 digits")
    .max(6, "OTP must be exactly 6 digits")
    .regex(/^[0-9]+$/, "OTP must contain only numbers"),
});

export default function VerifyOtpPage() {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isResending, setIsResending] = useState(false);
  const [otpValues, setOtpValues] = useState(new Array(6).fill(""));

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
    }
  }, [navigate]);

  const {
    handleSubmit,
    formState: { errors },
    setError,
    clearErrors,
  } = useForm({
    resolver: zodResolver(OtpSchema),
    defaultValues: { otp: "" },
  });

  const handleOtpChange = (index, value) => {
    if (value && !/^[0-9]$/.test(value)) return;
    const newOtpValues = [...otpValues];
    newOtpValues[index] = value;
    setOtpValues(newOtpValues);
    clearErrors("otp");

    if (value && index < 5) {
      document.querySelector(`input[name=otp-${index + 1}]`)?.focus();
    }
  };

  const onSubmit = async () => {
    setIsSubmitting(true);
    const otpString = otpValues.join("");
    const token = localStorage.getItem("token");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/verification/verify",
        { code: otpString },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        showToast.success("OTP verified successfully!");
        navigate("/dashboard");
      }
    } catch (error) {
      if (error.response) {
        const errorMessage =
          error.response.data?.message || "Verification failed";
        showToast.error(errorMessage);

        if (error.response.status === 401) {
          localStorage.removeItem("token");
          navigate("/login");
        }
      } else if (error.request) {
        showToast.error("Server not responding. Please try again later.");
      } else {
        showToast.error("An error occurred. Please try again.");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleResendOTP = async () => {
    setIsResending(true);
    const token = localStorage.getItem("token");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/verification/resend",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        showToast.success("New OTP has been sent!");
        setOtpValues(new Array(6).fill(""));
      }
    } catch (error) {
      if (error.response) {
        const errorMessage =
          error.response.data?.message || "Failed to resend OTP";
        showToast.error(errorMessage);

        if (error.response.status === 401) {
          localStorage.removeItem("token");
          navigate("/login");
        }
      } else if (error.request) {
        showToast.error("Server not responding. Please try again later.");
      } else {
        showToast.error("An error occurred. Please try again.");
      }
    } finally {
      setIsResending(false);
    }
  };

  return (
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
            We've sent a 6-digit verification code to your phone number.
          </p>
          <p className="text-center mt-2 text-sm">
            If this isn't your number?{" "}
            <span
              className="text-[#F04A23] cursor-pointer hover:underline"
              onClick={() => navigate("/phone")}
            >
              Change
            </span>
          </p>

          <form onSubmit={handleSubmit(onSubmit)} className="mt-6">
            <label className="font-medium text-sm block text-center">
              Verification code
            </label>
            <div className="flex gap-2 mt-2 justify-center">
              {otpValues.map((value, index) => (
                <input
                  key={index}
                  type="text"
                  name={`otp-${index}`}
                  maxLength={1}
                  value={value}
                  onChange={(e) => handleOtpChange(index, e.target.value)}
                  className="w-12 h-12 border-2 rounded-lg text-center text-lg focus:ring-[#F04A23] focus:border-[#F04A23]"
                  disabled={isSubmitting}
                />
              ))}
            </div>
            {errors.otp && (
              <p className="text-red-500 text-sm mt-2 text-center">
                {errors.otp.message}
              </p>
            )}

            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full bg-[#F04A23] text-white py-3 rounded-md hover:bg-[#d43d1a] transition-colors mt-6 disabled:opacity-50"
            >
              {isSubmitting ? "Verifying..." : "Verify"}
            </button>
          </form>

          <p className="text-right text-sm mt-4">
            Didn't receive it?{" "}
            <button
              type="button"
              onClick={handleResendOTP}
              disabled={isResending}
              className="text-[#F04A23] cursor-pointer hover:underline disabled:opacity-50"
            >
              {isResending ? "Resending..." : "Resend"}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}
