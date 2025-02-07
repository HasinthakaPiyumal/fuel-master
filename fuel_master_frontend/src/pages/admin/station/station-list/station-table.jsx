import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Edit } from "lucide-react";
import PropTypes from "prop-types";
import apiService from "@/services/api.service";
import { toast } from "@/hooks/use-toast";
import DialogDelete from "@/components/dialog-delete";
const StationsTable = ({ stations, refetch }) => {

  const handleDelete = async (id) => {
    try {
      const response = await apiService.delete(`/fuelstation/delete/${id}`);
      if (response.data.status === 200) {
        toast({
          title: 'Success',
          description: response.data.message,
          variant: 'default',
        })
        refetch();
      } else {
        toast({
          title: "Failed",
          description: response.data.message || "Something went wrong",
          variant: "destructive",
        });
      }
    } catch (error) {
      toast({
        title: "Failed",
        description: error.response?.data?.message || "Something went wrong",
        variant: "destructive",
      });
    }
  }

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>#</TableHead>
            <TableHead>Station Name</TableHead>
            <TableHead>Owner</TableHead>
            <TableHead>Created At</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {stations.map((station) => (
            <TableRow key={station.id}>
              <TableCell className="font-medium">{station.id}</TableCell>
              <TableCell>{station.location}</TableCell>
              <TableCell>{station.ownerName}</TableCell>
              <TableCell>{new Date(station.createdAt).toLocaleDateString()}</TableCell>
              <TableCell className="text-right">
                <Button variant="ghost" size="icon">
                  <Edit className="h-4 w-4" />
                </Button>
                <DialogDelete onDelete={() => handleDelete(station.id)} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

StationsTable.propTypes = {
  stations: PropTypes.array.isRequired,
  refetch: PropTypes.func.isRequired,
};

export default StationsTable;
