import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useForm } from "react-hook-form";

const AddVehicleTypes = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    trigger,
  } = useForm({
    defaultValues: {
      vehicleType: "",
      fuelType: "",
      defaultQuota: "",
    },
  });

  const onSubmit = (data) => {
    console.log(data);
  };

  const handleFuelTypeChange = (value) => {
    setValue("fuelType", value);
    trigger("fuelType");
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-6">Add Vehicle Type</h1>
      <Card className="max-w-md">
        <CardContent className="pt-6">
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div className="space-y-2">
              <label htmlFor="vehicleType" className="text-sm font-medium">
                Vehicle Type
              </label>
              <Input
                id="vehicleType"
                placeholder="Enter vehicle type"
                {...register("vehicleType", {
                  required: "Vehicle type is required",
                  minLength: {
                    value: 2,
                    message: "Vehicle type must be at least 2 characters",
                  },
                })}
              />
              {errors.vehicleType && (
                <p className="text-sm text-red-500 mt-1">
                  {errors.vehicleType.message}
                </p>
              )}
            </div>

            <div className="space-y-2">
              <label htmlFor="fuelType" className="text-sm font-medium">
                Fuel Type
              </label>
              <Select onValueChange={handleFuelTypeChange}>
                <SelectTrigger>
                  <SelectValue placeholder="Select fuel type" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="petrol">Petrol</SelectItem>
                  <SelectItem value="diesel">Diesel</SelectItem>
                  <SelectItem value="electric">Electric</SelectItem>
                  <SelectItem value="hybrid">Hybrid</SelectItem>
                </SelectContent>
              </Select>
              {errors.fuelType && (
                <p className="text-sm text-red-500 mt-1">
                  {errors.fuelType.message}
                </p>
              )}
            </div>

            <div className="space-y-2">
              <label htmlFor="defaultQuota" className="text-sm font-medium">
                Default Quota
              </label>
              <Input
                id="defaultQuota"
                type="number"
                placeholder="Enter default quota"
                {...register("defaultQuota", {
                  required: "Default quota is required",
                  validate: (value) =>
                    value > 0 || "Default quota must be greater than 0",
                })}
              />
              {errors.defaultQuota && (
                <p className="text-sm text-red-500 mt-1">
                  {errors.defaultQuota.message}
                </p>
              )}
            </div>

            <Button type="submit" className="w-full">
              Add Vehicle Type
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default AddVehicleTypes;
