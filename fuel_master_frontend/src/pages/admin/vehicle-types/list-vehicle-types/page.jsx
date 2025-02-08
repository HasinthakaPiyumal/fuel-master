import VehicleTypesTable from "./vehicle-types-table";
import { useQuery } from "@tanstack/react-query";
import apiService from "@/services/api.service";
import Loading from "@/components/loading";


const ListVehicleTypes = () => {
  const { data, isLoading, refetch } = useQuery({
    queryKey: ["vehicle-types"],
    queryFn: () => apiService.get("/vehicle-types/view"),
  });
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Vehicle Types List</h1>
      {isLoading ? <Loading /> : <VehicleTypesTable data={data.data.data} refetch={refetch} />}
    </div>
  );
};

export default ListVehicleTypes;
