import Loading from "@/components/loading";
import { AdminContext } from "@/context/AdminContext";
import apiService from "@/services/api.service";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { Navigate, Outlet } from "react-router-dom";


const ProtectedRoute = () => {
    const queryClient = useQueryClient();

    const { data: allData, isLoading } = useQuery({
        queryKey: ["user"],
        queryFn: async () => {
            const response = await apiService.get("/admin/me");
            return response.data.data;
        },
        retry: false
    });

    const user = allData?.user;

    if (isLoading) {
        return <Loading />;
    }

    if (!user) {
        queryClient.removeQueries(["user"]);
        return <Navigate to="/admin-login" />;
    }
    if (user.role !== "SUPER_ADMIN" && user.role !== "STATION_MANAGER") {
        return <Navigate to="/admin-login" />;
    }

    return <AdminContext.Provider value={{ user, userRole: user.role }}><Outlet /></AdminContext.Provider>;
};

export default ProtectedRoute;