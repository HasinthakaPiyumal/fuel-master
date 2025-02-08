import { useQuery } from '@tanstack/react-query';
import { LoginForm } from './LoginForm';
import Loading from '@/components/loading';
import { Navigate } from 'react-router-dom';
import apiService from '@/services/api.service';

const AdminLogin = () => {

    const { data: allData, isLoading } = useQuery({
        queryKey: ["user"],
        queryFn: async () => {
            const response = await apiService.get("/admin/me");
            return response.data.data;
        },
        retry: false
    });

    const user = allData?.user;

    if ((user && user.role === "SUPER_ADMIN") || (user && user.role === "STATION_MANAGER")) {
        return <Navigate to="/admin/dashboard" />;
    }

    return isLoading ? <Loading /> : (
        <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
            <div className="w-full max-w-sm">
                <LoginForm />
            </div>
        </div>
    );
};

export default AdminLogin; 