import PropTypes from 'prop-types'
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
import apiService from '@/services/api.service';
import DialogDelete from '@/components/dialog-delete';
import { toast } from '@/hooks/use-toast';
import UpdateQuota from './update-quota';
import { z } from 'zod';

const VehicleTypesTable = ({ data, refetch }) => {


  const handleDelete = async (id) => {
    try {
      const response = await apiService.delete(`/vehicle-types/delete/${id}`);
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
            <TableHead>Vehicle Type ID</TableHead>
            <TableHead>Vehicle Type</TableHead>
            <TableHead>Fuel Type</TableHead>
            <TableHead>Default Quota</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>{item.vehicleType}</TableCell>
              <TableCell>{item.fuelType}</TableCell>
              <TableCell>{item.defaultQuota}</TableCell>
              <TableCell className="text-right space-x-2">
                <UpdateQuota id={item.id} quota={item.defaultQuota} refetch={refetch} />
                <DialogDelete onDelete={() => handleDelete(item.id)} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

VehicleTypesTable.propTypes = {
  data: PropTypes.array.isRequired,
  refetch: PropTypes.func.isRequired,
};

export default VehicleTypesTable;
