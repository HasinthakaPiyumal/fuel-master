import UserTable from "./user-table"
import Loading from "@/components/loading"
import apiService from "@/services/api.service"
import { useQuery } from "@tanstack/react-query"

export default function Users() {
    const { data, isLoading, refetch } = useQuery({
        queryKey: ['users'],
        queryFn: () => apiService.get('/user/all'),
    })

    return isLoading ? <Loading /> : (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h1 className="text-3xl font-bold">Users</h1>
            </div>
            <UserTable users={data?.data.data?.allUsers || []} refetch={refetch} />
        </div>
    )
} 