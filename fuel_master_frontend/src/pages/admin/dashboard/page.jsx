import { Card } from "@/components/ui/card"
import {
    LineChart,
    BarChart,
    UserIcon,
    DollarSign,
    ShoppingCart,
    Activity
} from "lucide-react"

export default function Dashboard() {
    return (
        <div className="space-y-6">
            <h1 className="text-3xl font-bold">Dashboard</h1>

            <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <UserIcon className="h-10 w-10 text-blue-500" />
                        <div>
                            <p className="text-sm text-gray-500">Total Users</p>
                            <h3 className="text-2xl font-bold">1,234</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <ShoppingCart className="h-10 w-10 text-green-500" />
                        <div>
                            <p className="text-sm text-gray-500">Total Orders</p>
                            <h3 className="text-2xl font-bold">856</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <DollarSign className="h-10 w-10 text-yellow-500" />
                        <div>
                            <p className="text-sm text-gray-500">Revenue</p>
                            <h3 className="text-2xl font-bold">$12,345</h3>
                        </div>
                    </div>
                </Card>

                <Card className="p-6">
                    <div className="flex items-center space-x-4">
                        <Activity className="h-10 w-10 text-purple-500" />
                        <div>
                            <p className="text-sm text-gray-500">Active Users</p>
                            <h3 className="text-2xl font-bold">432</h3>
                        </div>
                    </div>
                </Card>
            </div>

            {/* Add more dashboard content as needed */}
        </div>
    )
} 