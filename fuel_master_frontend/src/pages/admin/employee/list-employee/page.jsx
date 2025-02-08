import apiService from '@/services/api.service';
import EmployeeTable from './employee-table';
import { useQuery } from '@tanstack/react-query';

const ListEmployee = () => {
    const { data: employees, isLoading: isLoadingEmployees, refetch } = useQuery({
        queryKey: ["employees"],
        queryFn: () => apiService.get("/employee/all"),
        retry: false
    });
    return (
        <div className="list-employee">
            <h1 className="text-2xl font-bold text-gray-800 mb-6 border-b pb-4">Employee List</h1>
            <EmployeeTable data={employees?.data?.data || []} isLoading={isLoadingEmployees} refetch={refetch} />
        </div>
    );
};


export default ListEmployee; 