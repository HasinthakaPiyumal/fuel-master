import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { MoreHorizontal, Pencil, Trash2 } from "lucide-react";

const StationsTable = () => {
  const stations = [
    {
      id: 1,
      name: "Station A",
      location: "Colombo",
      status: "active",
    },
    {
      id: 2,
      name: "Station B",
      location: "Kandy",
      status: "inactive",
    },
  ];

  const getStatusButton = (status) => {
    const statusStyles = {
      active: "bg-green-100 text-green-700 hover:bg-green-200 hover:shadow-md",
      inactive: "bg-red-100 text-red-700 hover:bg-red-200 hover:shadow-md",
    };

    return (
      <button
        className={`inline-flex items-center rounded-md px-2 py-1 text-xs font-medium transition-all duration-200 ease-in-out ${statusStyles[status]}`}
        onClick={() => console.log("Status changed")}
      >
        {status.charAt(0).toUpperCase() + status.slice(1)}
      </button>
    );
  };

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Station Name</TableHead>
            <TableHead>Location</TableHead>
            <TableHead>Status</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {stations.map((station) => (
            <TableRow key={station.id}>
              <TableCell className="font-medium">{station.name}</TableCell>
              <TableCell>{station.location}</TableCell>
              <TableCell>{getStatusButton(station.status)}</TableCell>
              <TableCell className="text-right">
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button variant="ghost" className="h-8 w-8 p-0">
                      <span className="sr-only">Open menu</span>
                      <MoreHorizontal className="h-4 w-4" />
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="end">
                    <DropdownMenuItem
                      onClick={() => console.log("Edit", station.id)}
                      className="cursor-pointer"
                    >
                      <Pencil className="mr-2 h-4 w-4" />
                      Edit
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onClick={() => console.log("Delete", station.id)}
                      className="cursor-pointer text-red-600"
                    >
                      <Trash2 className="mr-2 h-4 w-4" />
                      Delete
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default StationsTable;
