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

// Form validation schema
const formSchema = z.object({
  regNo: z.string().min(2, {
    message: "Registration number must be at least 2 characters.",
  }),
  location: z.string().min(2, {
    message: "Location must be at least 2 characters.",
  }),
  owner: z.string({
    required_error: "Please select an owner.",
  }),
  employeeCount: z.string().regex(/^\d+$/, {
    message: "Employee count must be a valid number.",
  }),
});

const NewStation = () => {
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      regNo: "",
      location: "",
      owner: "",
      employeeCount: "",
    },
  });

  function onSubmit(values) {
    console.log(values);
    // Handle form submission
  }

  // Mock data for owners - replace with your actual data
  const owners = [
    { id: "1", name: "John" },
    { id: "2", name: "Jane" },
    { id: "3", name: "Bob" },
  ];

  return (
    <div className="flex items-center justify-center h-[calc(100vh-80px)]">
      <Card className="max-w-xl w-full mx-4">
        <div className="p-6">
          <h1 className="text-2xl font-bold mb-6 text-center">
            Add New Station
          </h1>

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
                name="owner"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Owner</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select an owner" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {owners.map((owner) => (
                          <SelectItem key={owner.id} value={owner.id}>
                            {owner.name}
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
                name="employeeCount"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Employee Count</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        min="0"
                        step="1"
                        placeholder="10"
                        onKeyDown={(e) => {
                          if (
                            e.key === "-" ||
                            e.key === "." ||
                            e.key === "e" ||
                            e.key === "E"
                          ) {
                            e.preventDefault();
                          }
                        }}
                        onChange={(e) => {
                          const value = e.target.value;
                          if (value === "" || parseInt(value) < 0) {
                            field.onChange("0");
                          } else {
                            field.onChange(value);
                          }
                        }}
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full">
                Create Station
              </Button>
            </form>
          </Form>
        </div>
      </Card>
    </div>
  );
};

export default NewStation;
