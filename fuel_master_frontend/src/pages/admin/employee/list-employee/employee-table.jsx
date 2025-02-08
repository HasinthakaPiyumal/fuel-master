import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import PropTypes from 'prop-types'
import { alert } from '@/lib/alert';
import { useMutation } from '@tanstack/react-query';
import apiService from '@/services/api.service';
import DialogDelete from '@/components/dialog-delete';
import Loading from '@/components/loading';


const EmployeeTable = ({ data: employees, isLoading, refetch }) => {

  const { mutate } = useMutation({
    mutationKey: ["delete-employee"],
    onSuccess: () => {
      alert.success("Employee deleted successfully");
      refetch();
    },
    onError: (error) => {
      alert.error(error.response.data.message || "Something went wrong");
    },
    mutationFn: (employeeId) => apiService.delete(`/employee/delete/${employeeId}`),
  })
  const handleEdit = (employeeId) => {
    console.log("Edit employee:", employeeId);
  };

  return (
    isLoading ? <Loading /> :
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">#</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Phone</TableHead>
              <TableHead>NIC</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {employees.map((employee) => (
              <TableRow key={employee.id}>
                <TableCell className="font-medium">{employee.id}</TableCell>
                <TableCell>{employee.name}</TableCell>
                <TableCell>{employee.phone}</TableCell>
                <TableCell>{employee.nic}</TableCell>
                <TableCell className="text-right">
                  <div className="flex justify-end gap-2">
                    <DialogDelete onDelete={() => mutate(employee.id)} />
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
  );
};

EmployeeTable.propTypes = {
  data: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number,
    name: PropTypes.string,
    phone: PropTypes.string,
    nic: PropTypes.string,
  })).isRequired,
  isLoading: PropTypes.bool,
  refetch: PropTypes.func
}

export default EmployeeTable;