import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

const VehicleReportTable = () => {
  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Vehicle Type ID</TableHead>
            <TableHead>Vehicle Type</TableHead>
            <TableHead>Fuel Type</TableHead>
            <TableHead>Current Fuel Quota</TableHead>
            <TableHead>Fuel Usage</TableHead>
            <TableHead>Remaining Quota</TableHead>
            <TableHead>Last Refuel Date</TableHead>
            <TableHead>Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow>
            <TableCell>VT001</TableCell>
            <TableCell>Sedan</TableCell>
            <TableCell>Petrol</TableCell>
            <TableCell>100L</TableCell>
            <TableCell>45L</TableCell>
            <TableCell>55L</TableCell>
            <TableCell>2025-01-30</TableCell>
            <TableCell>
              <button
                className="inline-flex items-center rounded-md px-2 py-1 text-xs font-medium transition-all duration-200 ease-in-out bg-green-100 text-green-700 hover:bg-green-200 hover:shadow-md"
                onClick={() => console.log("Status changed")}
              >
                Active
              </button>
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell>VT002</TableCell>
            <TableCell>SUV</TableCell>
            <TableCell>Diesel</TableCell>
            <TableCell>150L</TableCell>
            <TableCell>80L</TableCell>
            <TableCell>70L</TableCell>
            <TableCell>2025-01-29</TableCell>
            <TableCell>
              <button
                className="inline-flex items-center rounded-md px-2 py-1 text-xs font-medium transition-all duration-200 ease-in-out bg-red-100 text-red-700 hover:bg-red-200 hover:shadow-md"
                onClick={() => console.log("Status changed")}
              >
                Inactive
              </button>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  );
};

export default VehicleReportTable;
