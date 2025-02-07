import { toast } from "@/hooks/use-toast";

export const alert = {
    success: (message, title = "Success") => {
        toast({
            title: title,
            description: message,
            variant: "default",
        });
    },
    error: (message, title = "Failed") => {
        toast({
            title: title,
            description: message,
            variant: "destructive",
        });
    },
};
