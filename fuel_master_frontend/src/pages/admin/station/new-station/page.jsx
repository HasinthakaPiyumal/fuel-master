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
  owner: z.string({
    required_error: "Please select an owner.",
  }),
  employeeCount: z
    .string()
    .transform((val) => parseInt(val) || 0)
    .pipe(
      z
        .number()
        .int("Employee count must be a whole number")
        .min(0, "Employee count cannot be negative")
    ),
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
  }

  const owners = [
    { id: "1", name: "John" },
    { id: "2", name: "Jane" },
    { id: "3", name: "Bob" },
  ];

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

            <Button type="submit">Create Station</Button>
          </form>
        </Form>
      </Card>
    </div>
  );
};
export default NewStation;
