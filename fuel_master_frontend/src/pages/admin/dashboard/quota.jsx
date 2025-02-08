import PropTypes from 'prop-types'
import { Avatar } from "@/components/ui/avatar"
import { Car } from 'lucide-react'

export function RecentActivity({ todayTransaction }) {
    todayTransaction = todayTransaction.slice(0, 5)
    return (
        <div className="space-y-8">
            {todayTransaction.map((activity, index) => (
                <div className="flex items-center" key={index}>
                    <Avatar className="h-9 w-9 bg-gradient-to-br from-blue-50 to-blue-100 shadow-sm flex items-center justify-center rounded-full border border-blue-200">
                        <Car className='h-5 w-5 text-blue-600' />
                    </Avatar>
                    <div className="ml-4 space-y-1">
                        <p className="text-sm font-medium leading-none capitalize">{activity.vehicle.vehicleType.vehicleType}: {activity.vehicle.vehicleRegistrationPart1 + " " + activity.vehicle.vehicleRegistrationPart2}
                        </p>
                        <p className="text-sm text-muted-foreground first-letter:capitalize">
                            pumped  {activity.pumpedQuantity}L of {activity.vehicle.vehicleType.fuelType.toLowerCase()} by {activity.employee.name}.
                        </p>
                    </div>
                    <div className="ml-auto font-medium">{activity.time}</div>
                </div>
            ))}
        </div>
    )
}

RecentActivity.propTypes = {
    todayTransaction: PropTypes.arrayOf(PropTypes.object).isRequired
}

