import Loading from "@/components/loading"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import apiService from "@/services/api.service"
import { useQuery } from "@tanstack/react-query"
import {
    UserIcon,
    Building2,
    Car,
    Fuel
} from "lucide-react"
import { Overview } from "./overview"
import { RecentActivity } from "./quota"

export default function Dashboard() {
    const { data, isLoading } = useQuery({
        queryKey: ["dashboard"],
        queryFn: () => apiService.get("/report/dashboard")
    })

    const dashboardData = data?.data?.data;

    return (
        isLoading ? <Loading /> : <div className="space-y-6">
            <h1 className="text-3xl font-bold">Dashboard</h1>

            <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">


                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <Fuel className="h-10 w-10 text-purple-500" />
                        <div>
                            <p className="text-sm text-gray-500">Today Used Fuel Amount</p>
                            <h3 className="text-2xl font-bold">{dashboardData.todayUsedFuelAmount} L</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <Building2 className="h-10 w-10 text-green-500" />
                        <div>
                            <p className="text-sm text-gray-500">Total Station</p>
                            <h3 className="text-2xl font-bold">{dashboardData.station}</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <UserIcon className="h-10 w-10 text-blue-500" />
                        <div>
                            <p className="text-sm text-gray-500">Total Employee</p>
                            <h3 className="text-2xl font-bold">{dashboardData.employee}</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <Car className="h-10 w-10 text-yellow-500" />
                        <div>
                            <p className="text-sm text-gray-500">Vehicle</p>
                            <h3 className="text-2xl font-bold">{dashboardData.vehicle}</h3>
                        </div>
                    </div>
                </Card>
            </div>
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
                <Card className="col-span-4">
                    <CardHeader>
                        <CardTitle>Overview</CardTitle>
                    </CardHeader>
                    <CardContent className="pl-2">
                        <Overview data={dashboardData.weeklyReport} />
                    </CardContent>
                </Card>
                <Card className="col-span-3">
                    <CardHeader>
                        <CardTitle>Recent Activity</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <RecentActivity todayTransaction={dashboardData.todayTransaction} />
                    </CardContent>
                </Card>
            </div>
        </div>
    )
} 