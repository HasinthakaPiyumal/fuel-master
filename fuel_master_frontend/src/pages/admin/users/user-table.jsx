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
const UserTable = ({ users, refetch }) => {

  const handleDelete = async (id) => {
    try {
      const response = await apiService.delete(`/user/delete/${id}`);
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
            <TableHead>Name</TableHead>
            <TableHead>Phone</TableHead>
            <TableHead>NIC</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {users.map((user) => (
            <TableRow key={user.id}>
              <TableCell className="font-medium">{user.id}</TableCell>
              <TableCell>{user.firstName} {user.lastName}</TableCell>
              <TableCell>{user.phone}</TableCell>
              <TableCell>{user.nic}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

UserTable.propTypes = {
  users: PropTypes.array.isRequired,
  refetch: PropTypes.func.isRequired,
};

export default UserTable;
