import React, { useState } from "react";
import FuelStationAnimation from "@/components/animation/FuelStationAnimation";
import { useNavigate } from "react-router-dom";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { toast } from "react-hot-toast";

// Zod validation schema
const OtpSchema = z.object({
  otp: z.string()
    .min(6, "OTP must be exactly 6 digits")
    .max(6, "OTP must be exactly 6 digits")
    .regex(/^[0-9]+$/, "OTP must contain only numbers")
    .refine((val) => val.length === 6, {
      message: "Please enter all 6 digits"
    }),
});

export default function VerifyOtpPage() {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [otpValues, setOtpValues] = useState(new Array(6).fill(""));

  const {
    handleSubmit,
    formState: { errors },
    setError,
    clearErrors,
  } = useForm({
    resolver: zodResolver(OtpSchema),
    defaultValues: {
      otp: "",
    },
  });

  // Handle OTP input change
  const handleOtpChange = (index, value) => {
    // Only allow numbers
    if (value && !/^\d*$/.test(value)) return;

    const newOtpValues = [...otpValues];
    newOtpValues[index] = value;
    setOtpValues(newOtpValues);

    // Clear any existing errors when user starts typing
    clearErrors("otp");

    // Auto-focus next input
    if (value && index < 5) {
      const nextInput = document.querySelector(`input[name=otp-${index + 1}]`);
      if (nextInput) nextInput.focus();
    }

    // Handle backspace
    if (value === "" && index > 0) {
      const prevInput = document.querySelector(`input[name=otp-${index - 1}]`);
      if (prevInput) prevInput.focus();
    }
  };

  // Handle key press
  const handleKeyDown = (index, e) => {
    if (e.key === "Backspace" && !otpValues[index] && index > 0) {
      const prevInput = document.querySelector(`input[name=otp-${index - 1}]`);
      if (prevInput) {
        prevInput.focus();
        const newOtpValues = [...otpValues];
        newOtpValues[index - 1] = "";
        setOtpValues(newOtpValues);
      }
    }
  };

  // Handle paste
  const handlePaste = (e) => {
    e.preventDefault();
    const pastedData = e.clipboardData.getData("text").slice(0, 6);
    if (!/^\d+$/.test(pastedData)) {
      toast.error("Only numbers are allowed");
      return;
    }
    
    const newOtpValues = [...otpValues];
    pastedData.split("").forEach((char, index) => {
      if (index < 6) newOtpValues[index] = char;
    });
    setOtpValues(newOtpValues);
  };

  const onSubmit = async () => {
    try {
      setIsSubmitting(true);
      const otpString = otpValues.join("");

      // Validate complete OTP
      const validation = OtpSchema.safeParse({ otp: otpString });
      if (!validation.success) {
        setError("otp", {
          type: "manual",
          message: validation.error.errors[0].message,
        });
        return;
      }

      // API call to verify OTP
      const response = await fetch("/api/verify-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ otp: otpString }),
      });

      if (!response.ok) {
        throw new Error("Invalid OTP");
      }

      toast.success("OTP verified successfully!");
      navigate("/dashboard"); // Navigate to next page on success
    } catch (error) {
      toast.error(error.message || "Failed to verify OTP");
      setError("otp", {
        type: "manual",
        message: "Invalid OTP. Please try again.",
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className>
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col lg:flex-row items-center justify-center gap-8">
        <div className="lg:w-1/2">
          <FuelStationAnimation />
        </div>

          {/* Right Side - Form */}
          <div className="flex-1 flex items-center justify-center">
            <div className="bg-white rounded-lg p-8 w-[450px] shadow-sm">
              <h1 className="text-[#F04A23] text-2xl font-semibold text-center">
                Verify OTP
              </h1>
              
              <p className="text-gray-600 text-center mt-4">
                We've sent a 6-digit verification code to 076 321 5389. Please enter the OTP below.
              </p>
              
              <p className="text-center mt-2">
                <span className="text-sm">
                  If this isn't your number?{" "}
                  <button 
                    onClick={() => navigate('/phone')}
                    className="text-[#F04A23] hover:underline"
                  >
                    Change
                  </button>
                </span>
              </p>

              <form onSubmit={handleSubmit(onSubmit)}>
                <div className="mt-6">
                  <label className="text-gray-600 text-sm">Verification code</label>
                  <div className="flex gap-2 mt-2 justify-center">
                    {otpValues.map((value, index) => (
                      <input
                        key={index}
                        type="text"
                        name={`otp-${index}`}
                        maxLength={1}
                        value={value}
                        onChange={(e) => handleOtpChange(index, e.target.value)}
                        onKeyDown={(e) => handleKeyDown(index, e)}
                        onPaste={handlePaste}
                        className={`w-12 h-12 border-2 ${
                          errors.otp ? 'border-red-500' : 'border-gray-200'
                        } rounded text-center text-lg focus:border-[#F04A23] focus:outline-none`}
                        disabled={isSubmitting}
                      />
                    ))}
                  </div>
                  {errors.otp && (
                    <p className="text-red-500 text-sm mt-2 text-center">
                      {errors.otp.message}
                    </p>
                  )}
                </div>

                <button 
                  type="submit"
                  disabled={isSubmitting}
                  className={`w-full bg-[#F04A23] text-white py-3 rounded-md hover:bg-[#d43d1a] transition-colors mt-6 ${
                    isSubmitting ? 'opacity-50 cursor-not-allowed' : ''
                  }`}
                >
                  {isSubmitting ? 'Verifying...' : 'Verify'}
                </button>
              </form>

              <p className="text-center text-sm mt-4">
                Didn't receive it?{" "}
                <button 
                  className="text-[#F04A23] hover:underline"
                  disabled={isSubmitting}
                >
                  Resend
                </button>
              </p>
            </div>
          </div>
        </div>
      </div>

    
    </div>
  );
}