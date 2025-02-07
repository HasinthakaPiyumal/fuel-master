import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"
import apiService from "@/services/api.service"
import PropTypes from 'prop-types'
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { toast } from "@/hooks/use-toast"

const loginSchema = z.object({
    email: z.string().email("Please enter a valid email address"),
    password: z.string().min(1, "Password is required"),
})

export function LoginForm({
    className,
    ...props
}) {
    const [isLoading, setIsLoading] = useState(false)
    const navigate = useNavigate()

    const form = useForm({
        resolver: zodResolver(loginSchema),
        defaultValues: {
            email: "",
            password: "",
        },
    })

    async function onSubmit(values) {
        setIsLoading(true)
        try {
            const response = await apiService.post("/admin/login", values)
            if (response.data.status !== 200) {
                throw new Error(response.data.message)
            }
            localStorage.setItem("token", response.data.data.token)
            setIsLoading(false)
            toast({
                title: "Login successful",
                description: "You are now logged in",
                variant: "default",
            })
            navigate("/admin/dashboard")
        } catch (error) {
            toast({
                title: "Login failed",
                description: error.response.data.message || "Something went wrong",
                variant: "destructive",
            })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className={cn("flex flex-col gap-6", className)} {...props}>
            <Card>
                <CardHeader>
                    <CardTitle className="text-2xl text-center">Login</CardTitle>
                </CardHeader>
                <CardContent>
                    <form onSubmit={form.handleSubmit(onSubmit)}>
                        <div className="flex flex-col gap-6">
                            <div className="grid gap-2">
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    {...form.register("email")}
                                />
                                {form.formState.errors.email && (
                                    <p className="text-sm text-red-500">
                                        {form.formState.errors.email.message}
                                    </p>
                                )}
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="password">Password</Label>
                                <Input
                                    id="password"
                                    type="password"
                                    {...form.register("password")}
                                />
                                {form.formState.errors.password && (
                                    <p className="text-sm text-red-500">
                                        {form.formState.errors.password.message}
                                    </p>
                                )}
                            </div>
                            <Button
                                type="submit"
                                className="w-full"
                                loading={isLoading}
                            >
                                Login
                            </Button>
                        </div>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}

LoginForm.propTypes = {
    className: PropTypes.string
}
