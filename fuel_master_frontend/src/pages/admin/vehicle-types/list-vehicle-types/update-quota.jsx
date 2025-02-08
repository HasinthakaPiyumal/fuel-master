import { Button } from '@/components/ui/button'
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { alert } from '@/lib/alert'
import apiService from '@/services/api.service'
import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation } from '@tanstack/react-query'
import { Edit } from 'lucide-react'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import React from 'react'


const schema = z.object({
    quota: z.coerce.number().min(1, { message: "Quota must be at least 1." }),
});


const UpdateQuota = ({ id, quota, refetch }) => {
    const { register, handleSubmit, formState: { errors }, reset } = useForm({
        resolver: zodResolver(schema),
        defaultValues: {

        }
    });

    const [open, setOpen] = React.useState(false);

    const { mutate: updateQuota, isPending } = useMutation({
        mutationFn: (data) => apiService.put(`/vehicle-types/change-quota/${data.id}`, data),
        onSuccess: () => {
            alert.success("Quota updated successfully");
            setOpen(false);
            reset();
            refetch();
        },
        onError: () => {
            alert.error("Failed to update quota");
        }
    });


    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
                <Button variant="ghost" size="icon">
                    <Edit className="h-4 w-4" />
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Update Quota</DialogTitle>
                    <DialogDescription>
                        Update the quota for this vehicle type.
                    </DialogDescription>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="items-center gap-4">
                        <Label htmlFor="quota" className="text-right">
                            Quota
                        </Label>
                        <Input id="quota" type="number" {...register("quota")} />
                    </div>
                    <p className="text-red-500 text-sm">{errors.quota?.message}</p>
                </div>
                <DialogFooter>
                    <Button loading={isPending} type="submit" onClick={handleSubmit(payload => updateQuota({ id, defaultQuota: payload.quota }))}>Save changes</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}

export default UpdateQuota