import { useState, useEffect } from "react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios"; 

const vehicleSchema = z.object({
  vehicleNumber: z.object({
    prefix: z
      .string()
      .min(1, "Prefix is required")
      .regex(/^[A-Z]+$/, "Prefix must be uppercase letters only"),
    number: z.string().min(1, "Number is required"),
  }),
  vehicleType: z.string().min(1, "Vehicle type is required"),
  chassisNumber: z
    .string()
    .min(1, "Chassis number is required")
    .regex(
      /^[A-Z0-9]+$/,
      "Chassis number must be uppercase letters and numbers only"
    ),
  fuelType: z.enum(["Petrol", "Diesel"], {
    required_error: "Please select a fuel type",
  }),
  termsAccepted: z.literal(true, {
    errorMap: () => ({ message: "You must accept the terms and conditions" }),
  }),
});

const Dashboard = () => {
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [userInfo, setUserInfo] = useState(null); 

  const {
    register,
    handleSubmit,
    setValue,
    watch,
    reset,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(vehicleSchema),
    defaultValues: {
      termsAccepted: false,
      fuelType: "Petrol",
    },
  });

  const handleUpperCase = (e, field) => {
    const value = e.target.value.toUpperCase();
    setValue(field, value);
  };

  const selectedFuelType = watch("fuelType");

  const onSubmit = (data) => {
    console.log(data);
   
    axios
      .post(
        "http://localhost:8080/api/v1/vehicle/save",
        {
          userId: 1, 
          vehicleType: data.vehicleType === "Car" ? 1 : data.vehicleType === "Bike" ? 2 : 3,
          vehicleRegistrationPart1: data.vehicleNumber.prefix,
          vehicleRegistrationPart2: data.vehicleNumber.number,
          chassisNumber: data.chassisNumber,
          fuelType: data.fuelType.toUpperCase(),
        },
        {
          headers: {
            
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        setShowSuccessModal(true);
        reset();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  useEffect(() => {
   
    axios
      .get("http://localhost:8080/api/v1/user/1", {
        headers: {
          
        },
      })
      .then((response) => {
        setUserInfo(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <div className="">
      {showSuccessModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 max-w-sm mx-4">
            <div className="text-center">
              <div className="text-green-500 text-4xl mb-4">✓</div>
              <h3 className="text-xl font-medium mb-4">Success!</h3>
              <p className="text-gray-600 mb-6">
                Vehicle has been registered successfully.
              </p>
              <button
                onClick={() => setShowSuccessModal(false)}
                className="bg-[#F84C25] text-white px-6 py-2 rounded hover:bg-[#e64421]"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="max-w-6xl mx-auto p-8 flex gap-16">
       
        <div className="w-full lg:w-1/2">
          <div className="bg-white rounded-lg p-6 shadow-lg">
            <h2 className="text-[#F84C25] text-xl text-center mb-6">Your Info</h2>
            <div className="space-y-4">
              {userInfo ? (
                <>
                  <div className="flex justify-between">
                    <span className="text-gray-700 font-medium">Name:</span>
                    <span>{userInfo.name}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-700 font-medium">NIC:</span>
                    <span>{userInfo.nic}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-700 font-medium">Phone:</span>
                    <span>{userInfo.phoneNumber}</span>
                  </div>
                </>
              ) : (
                <p>Loading...</p>
              )}
            </div>
          </div>
        </div>

        <div className="w-[602px] h-[648px] lg:w-1/2">
          <div className="bg-white rounded-lg p-10 shadow-lg">
            <h2 className="text-[#F84C25] text-xl text-center mb-6">Vehicle Info</h2>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
             
              <div>
                <label className="block mb-1">Vehicle Number</label>
                <div className="flex gap-2">
                  <input
                    type="text"
                    placeholder="Ex: ABC"
                    className="border rounded p-1.5 w-1/3 text-sm uppercase"
                    {...register("vehicleNumber.prefix")}
                    onChange={(e) => handleUpperCase(e, "vehicleNumber.prefix")}
                  />
                  <input
                    type="number"
                    placeholder="Ex: 8822"
                    className="border rounded p-1.5 w-2/3 text-sm"
                    {...register("vehicleNumber.number")}
                  />
                </div>
                {errors.vehicleNumber?.prefix && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.vehicleNumber.prefix.message}
                  </p>
                )}
                {errors.vehicleNumber?.number && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.vehicleNumber.number.message}
                  </p>
                )}
              </div>

             
              <div>
                <label className="block mb-1">Vehicle Type</label>
                <select
                  className="w-full border rounded p-1.5 text-sm text-gray-500"
                  {...register("vehicleType")}
                >
                  <option value="Car">Car</option>
                  <option value="Bike">Bike</option>
                  <option value="Van">Van</option>
                </select>
                {errors.vehicleType && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.vehicleType.message}
                  </p>
                )}
              </div>

              <div>
                <label className="block mb-1">Chassis Number</label>
                <input
                  type="text"
                  placeholder="Ex: NHKSCM2"
                  className="w-full border rounded p-1.5 text-sm uppercase"
                  {...register("chassisNumber")}
                  onChange={(e) => handleUpperCase(e, "chassisNumber")}
                />
                {errors.chassisNumber && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.chassisNumber.message}
                  </p>
                )}
              </div>

           
              <div>
                <label className="block mb-1">Select Fuel Type</label>
                <div className="flex gap-2">
                  <button
                    type="button"
                    onClick={() => setValue("fuelType", "Petrol")}
                    className={`px-4 py-1 rounded text-sm ${
                      selectedFuelType === "Petrol"
                        ? "bg-[#F84C25] text-white"
                        : "border"
                    }`}
                  >
                    Petrol
                  </button>
                  <button
                    type="button"
                    onClick={() => setValue("fuelType", "Diesel")}
                    className={`px-4 py-1 rounded text-sm ${
                      selectedFuelType === "Diesel"
                        ? "bg-[#F84C25] text-white"
                        : "border"
                    }`}
                  >
                    Diesel
                  </button>
                </div>
                {errors.fuelType && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.fuelType.message}
                  </p>
                )}
              </div>

             
              <div className="text-sm">
                <label className="flex items-center gap-1">
                  <input type="checkbox" {...register("termsAccepted")} />
                  <span>
                    I agree to{" "}
                    <a href="#" className="text-[#F84C25]">
                      Terms and Conditions
                    </a>
                  </span>
                </label>
                {errors.termsAccepted && (
                  <p className="text-red-500 text-xs mt-1">
                    {errors.termsAccepted.message}
                  </p>
                )}
              </div>

              <button
                type="submit"
                className="w-full bg-[#F84C25] text-white rounded py-2 text-sm cursor-pointer hover:bg-[#e64421]"
              >
                Register Vehicle
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
