import Loading from "@/components/loading";
import StationTable from "./station-table";
import apiService from "@/services/api.service";
import { useQuery } from "@tanstack/react-query";

const StationList = () => {
  const { data: stations, isLoading, refetch } = useQuery({
    queryKey: ["stations"],
    queryFn: () => apiService.get("/fuelstation/all"),
  });
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Stations List</h1>
      {isLoading ? <Loading /> : <StationTable stations={stations?.data?.data || []} refetch={refetch} />}
    </div>
  );
};

export default StationList;
