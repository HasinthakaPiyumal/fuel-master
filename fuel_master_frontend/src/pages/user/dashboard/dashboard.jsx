import { useState, useEffect } from "react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { showToast } from "@/hooks/use-toast";
import apiService from "@/services/api.service";
import { useQuery } from "@tanstack/react-query";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Loader2 } from "lucide-react";
import { Form } from "@/components/ui/form";
import { Navigate, useNavigate } from "react-router-dom";
import Loading from "@/components/loading";

const vehicleSchema = z.object({
  vehicleNumber: z.object({
    prefix: z
      .string()
      .min(1, "Prefix is required")
      .regex(/^[A-Z]+$/, "Prefix must be uppercase letters only"),
    number: z.string().min(1, "Number is required"),
  }),
  vehicleType: z.number({
    required_error: "Vehicle type is required",
    invalid_type_error: "Vehicle type must be a number",
  }).min(1, "Vehicle type is required"),
  chassisNumber: z
    .string()
    .min(1, "Chassis number is required")
    .regex(
      /^[A-Z0-9]+$/,
      "Chassis number must be uppercase letters and numbers only"
    ),
  fuelType: z.enum(["PETROL", "DIESEL"], {
    required_error: "Please select a fuel type",
  }),
  termsAccepted: z.literal(true, {
    errorMap: () => ({ message: "You must accept the terms and conditions" }),
  }),
});

const Dashboard = () => {
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [userInfo, setUserInfo] = useState(null);
  const [loading, setLoading] = useState(false);

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

  const { data: vehicleTypesData, isLoading: isLoadingVehicleTypes, error: vehicleTypesError } = useQuery({
    queryKey: ["vehicleTypes"],
    queryFn: async () => {
      const response = await apiService.get("/vehicle-types/view");
      if (!response.data.data) {
        throw new Error("No vehicle types found");
      }
      return response.data.data;
    },
  });

  const vehicleTypes = vehicleTypesData || [];

  const groupedVehicles = Object.values(
    vehicleTypes.reduce((acc, { vehicleType, fuelType, defaultQuota, id }) => {
      if (!acc[vehicleType]) {
        acc[vehicleType] = { vehicleType, fuelTypes: [], defaultQuota, id };
      }
      if (!acc[vehicleType].fuelTypes.includes(fuelType)) {
        acc[vehicleType].fuelTypes.push(fuelType);
      }
      return acc;
    }, {})
  );

  const form = useForm({
    resolver: zodResolver(vehicleSchema),
    defaultValues: {
      termsAccepted: false
    },
  });

  const handleUpperCase = (e, field) => {
    const value = e.target.value.toUpperCase();
    form.setValue(field, value);
  };

  const selectedFuelType = form.watch("fuelType");

  const getCurrentVehicleType = () => {
    if (!form.getValues("vehicleType")) {
      return null;
    }
    const tempVehicleType = vehicleTypes.find(type => type.id === form.getValues("vehicleType"));
    const currentVehicleType = vehicleTypes.find(type => type.fuelType === form.getValues("fuelType").toUpperCase() && type.vehicleType === tempVehicleType.vehicleType);
    return currentVehicleType;
  }

  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const vehicleData = {
        userId: userInfo?.id,
        vehicleType: getCurrentVehicleType().id,
        vehicleRegistrationPart1: data.vehicleNumber.prefix,
        vehicleRegistrationPart2: data.vehicleNumber.number,
        chassisNumber: data.chassisNumber,
        fuelType: data.fuelType.toUpperCase(),
      };

      const response = await apiService.post("/vehicle/save", vehicleData);

      if (response.status === 200) {
        showToast.success("Vehicle registered successfully!");
        setShowSuccessModal(true);
        form.reset();
      }
    } catch (error) {
      if (error.response) {
        showToast.error(error.response.data?.message || "Failed to register vehicle");
      } else if (error.request) {
        showToast.error("Server not responding. Please try again later.");
      } else {
        showToast.error("An error occurred. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await apiService.get("/user/authenticate");
        if (response.status === 200) {
          setUserInfo(response.data.data.user);
        }
      } catch (error) {
        if (error.response) {
          showToast.error(error.response.data?.message || "Failed to fetch user info");
        } else if (error.request) {
          showToast.error("Server not responding. Please try again later.");
        } else {
          showToast.error("An error occurred. Please try again.");
        }
      }
    };

    fetchUserInfo();
  }, []);

  const handleVehicleTypeChange = (value) => {
    form.setValue("vehicleType", parseInt(value));
    form.setValue("fuelType", undefined);
    const selectedVehicleType = groupedVehicles.find(type => type.id === parseInt(value));
    if (selectedVehicleType) {
      form.setValue("availableFuelTypes", selectedVehicleType.fuelTypes);
      form.setValue("fuelType", selectedVehicleType.fuelTypes[0]);
    }
  }

  if (!isLoading && !user) {
    return <Navigate to="/login" />;
  }
  if (!isLoading && !user.verified) {
    return <Navigate to="/otp" />;
  }
  if (!isLoading && user.verified && allData.vehicleRegistration) {
    return <Navigate to="/dashboard" />;
  }

  return isLoading ? <Loading /> : (
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
                onClick={() => { setShowSuccessModal(false); navigate('/dashboard') }}
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
                    <span>{userInfo.firstName} {userInfo.lastName}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-700 font-medium">NIC:</span>
                    <span>{userInfo.nic}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-700 font-medium">Phone:</span>
                    <span>{userInfo.phone}</span>
                  </div>
                </>
              ) : (
                <Loading />
              )}
            </div>
          </div>
        </div>

        <div className="w-[602px] h-[648px] lg:w-1/2">
          <div className="bg-white rounded-lg p-10 shadow-lg">
            <h2 className="text-[#F84C25] text-xl text-center mb-6">Vehicle Info</h2>
            <Form {...form}>
              <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                <div>
                  <label className="block mb-1">Vehicle Number</label>
                  <div className="flex gap-2">
                    <input
                      type="text"
                      placeholder="Ex: ABC"
                      className="border rounded p-1.5 w-1/3 text-sm uppercase"
                      {...form.register("vehicleNumber.prefix")}
                      onChange={(e) => handleUpperCase(e, "vehicleNumber.prefix")}
                    />
                    <input
                      type="number"
                      placeholder="Ex: 8822"
                      className="border rounded p-1.5 w-2/3 text-sm"
                      {...form.register("vehicleNumber.number")}
                    />
                  </div>
                  {form.formState.errors.vehicleNumber?.prefix && (
                    <p className="text-red-500 text-xs mt-1">
                      {form.formState.errors.vehicleNumber.prefix.message}
                    </p>
                  )}
                  {form.formState.errors.vehicleNumber?.number && (
                    <p className="text-red-500 text-xs mt-1">
                      {form.formState.errors.vehicleNumber.number.message}
                    </p>
                  )}
                </div>

                <FormField
                  control={form.control}
                  name="vehicleType"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Vehicle Type</FormLabel>
                      <Select
                        onValueChange={handleVehicleTypeChange}
                        defaultValue={field.value?.toString()}
                        disabled={isLoadingVehicleTypes || vehicleTypesError}
                      >
                        <FormControl>
                          <SelectTrigger>
                            {isLoadingVehicleTypes ? (
                              <Loader2 className="w-4 h-4 animate-spin" />
                            ) : (
                              <SelectValue placeholder="Select a vehicle type" />
                            )}
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          {groupedVehicles.map((type) => (
                            <SelectItem key={type.id} value={type.id.toString()}>
                              {type.vehicleType}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <div>
                  <label className="block mb-1">Chassis Number</label>
                  <input
                    type="text"
                    placeholder="Ex: NHKSCM2"
                    className="w-full border rounded p-1.5 text-sm uppercase"
                    {...form.register("chassisNumber")}
                    onChange={(e) => handleUpperCase(e, "chassisNumber")}
                  />
                  {form.formState.errors.chassisNumber && (
                    <p className="text-red-500 text-xs mt-1">
                      {form.formState.errors.chassisNumber.message}
                    </p>
                  )}
                </div>

                {
                  form.getValues("availableFuelTypes") && <div>
                    <label className="block mb-1">Select Fuel Type</label>
                    <div className="flex gap-2">
                      {
                        form.getValues("availableFuelTypes")
                          ?.map((fuelType) => (
                            <button
                              key={fuelType}
                              type="button"
                              onClick={() => form.setValue("fuelType", fuelType)}
                              className={`px-4 py-1 rounded text-sm ${selectedFuelType === fuelType
                                ? "bg-[#F84C25] text-white"
                                : "border"
                                }`}
                            >
                              {fuelType.charAt(0) + fuelType.slice(1).toLowerCase()}
                            </button>
                          ))
                      }
                    </div>
                    {form.formState.errors.fuelType && (
                      <p className="text-red-500 text-xs mt-1">
                        {form.formState.errors.fuelType.message}
                      </p>
                    )}
                  </div>}

                <div className="text-sm">
                  <label className="flex items-center gap-1">
                    <input type="checkbox" {...form.register("termsAccepted")} />
                    <span>
                      I agree to{" "}
                      <a href="#" className="text-[#F84C25]">
                        Terms and Conditions
                      </a>
                    </span>
                  </label>
                  {form.formState.errors.termsAccepted && (
                    <p className="text-red-500 text-xs mt-1">
                      {form.formState.errors.termsAccepted.message}
                    </p>
                  )}
                </div>

                <button
                  type="submit"
                  disabled={loading}
                  className="w-full bg-[#F84C25] text-white rounded py-2 text-sm cursor-pointer hover:bg-[#e64421] disabled:opacity-50"
                >
                  {loading ? "Registering..." : "Register Vehicle"}
                </button>
              </form>
            </Form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
