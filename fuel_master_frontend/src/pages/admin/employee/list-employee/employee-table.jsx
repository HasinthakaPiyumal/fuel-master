import React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"
import { Edit, Trash2 } from "lucide-react"


const employees = [
  {
    id: "EMP001",
    name: "Navon Sanjuni",
    phone: "0771234567",
    nic: "200112345678",
  },
  {
    id: "EMP002",
    name: "Tasheen Darshika",
    phone: "0777654321",
    nic: "200287654321",
  },
  {
    id: "EMP003",
    name: "Hiruni Imasha",
    phone: "0777654322",
    nic: "200287654331",
  },
 
];

const EmployeeTable = () => {
  const handleEdit = (employeeId) => {
    console.log("Edit employee:", employeeId);
    
  };

  const handleDelete = (employeeId) => {
    console.log("Delete employee:", employeeId);
    
  };

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[100px]">Employee ID</TableHead>
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
                  <Button
                    variant="ghost"
                    size="icon"
                    onClick={() => handleEdit(employee.id)}
                  >
                    <Edit className="h-4 w-4 text-blue-500" />
                  </Button>
                  <Button
                    variant="ghost"
                    size="icon"
                    onClick={() => handleDelete(employee.id)}
                  >
                    <Trash2 className="h-4 w-4 text-red-500" />
                  </Button>
                </div>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default EmployeeTable;