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
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Card } from "@/components/ui/card";
import { useMutation, useQuery } from "@tanstack/react-query";
import apiService from "@/services/api.service";
import { toast } from "@/hooks/use-toast";
import { Loader2 } from "lucide-react";

const formSchema = z.object({
  regNo: z
    .string()
    .min(2, "Registration number must be at least 2 characters.")
    .regex(
      /^[A-Za-z0-9-]+$/,
      "Registration number can only contain letters, numbers, and hyphens"
    ),
  location: z.string().min(2, {
    message: "Location must be at least 2 characters.",
  }),
  ownerId: z.number({
    required_error: "Please select an owner.",
    invalid_type_error: "Please select an owner.",
  }).positive("Please select an owner."),
});

const NewStation = () => {
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      regNo: "",
      location: "",
      ownerId: 0,
      employeeCount: "",
    },
  });

  const { data: managers, isLoading: isLoadingManagers, refetch, error: errorManagers } = useQuery({
    queryKey: ["unassigned-station-managers"],
    queryFn: () => apiService.get("/admin/unassigned-station-managers"),
  });

  const { mutate, isLoading } = useMutation({
    onSuccess: () => {
      toast({
        title: "Success",
        description: "Station added successfully",
        variant: "default",
      });
      refetch();
      form.reset();
    },
    onError: (error) => {
      toast({
        title: "Failed",
        description: error.response.data.message,
        variant: "destructive",
      });
    },
    mutationFn: async (values) => { return await apiService.post("/fuelstation/save", values) },
  });

  const onSubmit = async (values) => {
    mutate(values);
  }

  const owners = errorManagers ? [] : managers?.data?.data?.map((manager) => ({
    id: manager.id,
    name: manager.name,
  })) || [];

  const handleOwnerChange = (value) => {
    form.setValue("ownerId", parseInt(value));
    form.trigger("ownerId");
  };


  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Add New Station</h1>
      <Card className="max-w-xl w-full p-6">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
            <FormField
              control={form.control}
              name="regNo"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Registration Number</FormLabel>
                  <FormControl>
                    <Input placeholder="FS33234" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="location"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Location</FormLabel>
                  <FormControl>
                    <Input placeholder="Athurugiriya" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="ownerId"
              render={() => (
                <FormItem>
                  <FormLabel>Owner</FormLabel>
                  <Select
                    onValueChange={handleOwnerChange}
                    disabled={owners.length === 0}
                    value={errorManagers ? 0 : form.getValues("ownerId")}
                  >
                    <FormControl >
                      <SelectTrigger>
                        {owners.length === 0 && "No Active Station Managers"}
                        <SelectValue value={0} placeholder={"Select an owner"} />
                      </SelectTrigger>
                    </FormControl>
                    {(!isLoadingManagers || owners.length > 0) ? <SelectContent>
                      {owners.map((manager) => (
                        <SelectItem key={manager.id} value={manager.id}>
                          {manager.name}
                        </SelectItem>
                      ))}
                    </SelectContent> : <SelectContent>
                      <SelectItem key={0} value="0">
                        <Loader2 className="w-4 h-4 animate-spin" />
                      </SelectItem>
                    </SelectContent>}
                  </Select>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button type="submit" loading={isLoading}>Create Station</Button>
          </form>
        </Form>
      </Card>
    </div >
  );
};
export default NewStation;
