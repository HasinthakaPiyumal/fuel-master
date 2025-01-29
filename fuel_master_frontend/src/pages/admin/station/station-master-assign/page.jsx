import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { showToast } from "@/hooks/use-toast";

const formSchema = z.object({
  stationId: z.string().min(1, "Please select a station"),
  stationMasterId: z.string().min(1, "Please select a station master"),
});

export default function StationMasterAssign() {
  const [loading, setLoading] = useState(false);

  const stations = [
    { id: "1", name: "Station A" },
    { id: "2", name: "Station B" },
    { id: "3", name: "Station C" },
  ];

  const stationMasters = [
    { id: "1", name: "John Doe" },
    { id: "2", name: "Jane Smith" },
    { id: "3", name: "Bob Johnson" },
  ];

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      stationId: "",
      stationMasterId: "",
    },
  });

  const onSubmit = async (data) => {
    try {
      if (!data.stationId || !data.stationMasterId) {
        showToast.error("Please select both station and station master");
        return;
      }

      setLoading(true);

      await new Promise((resolve) => setTimeout(resolve, 1000));
      console.log("Form submitted:", data);

      showToast.success("Station master assigned successfully!");
      form.reset();
    } catch (error) {
      console.error("Submission error:", error);
      showToast.error("Failed to assign station master. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center h-[calc(100vh-80px)]">
      <Card className="max-w-xl w-full mx-4">
        <CardHeader>
          <CardTitle className="text-2xl font-bold text-center">
            Assign Station Master
          </CardTitle>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="stationId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Station</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select a station" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {stations.map((station) => (
                          <SelectItem key={station.id} value={station.id}>
                            {station.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="stationMasterId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Station Master</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select a station master" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {stationMasters.map((master) => (
                          <SelectItem key={master.id} value={master.id}>
                            {master.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full" disabled={loading}>
                {loading ? "Assigning..." : "Assign Station Master"}
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
