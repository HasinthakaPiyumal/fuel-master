import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const AddVehicleTypes = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission
  };

  return (
    <div className="max-w-2xl mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle>Add Vehicle Type</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <label htmlFor="vehicleType" className="text-sm font-medium">
                Vehicle Type
              </label>
              <Input id="vehicleType" placeholder="Enter vehicle type" />
            </div>

            <div className="space-y-2">
              <label htmlFor="fuelType" className="text-sm font-medium">
                Fuel Type
              </label>
              <Select>
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
            </div>

            <div className="space-y-2">
              <label htmlFor="defaultQuota" className="text-sm font-medium">
                Default Quota
              </label>
              <Input
                id="defaultQuota"
                type="number"
                placeholder="Enter default quota"
              />
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
