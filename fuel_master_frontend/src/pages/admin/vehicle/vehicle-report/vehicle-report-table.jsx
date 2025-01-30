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
          {/* Example row - Replace with your data mapping */}
          <TableRow>
            <TableCell>VT001</TableCell>
            <TableCell>Sedan</TableCell>
            <TableCell>Petrol</TableCell>
            <TableCell>100L</TableCell>
            <TableCell>45L</TableCell>
            <TableCell>55L</TableCell>
            <TableCell>2025-01-30</TableCell>
            <TableCell>
              <span className="inline-flex items-center rounded-full px-2 py-1 text-xs font-medium ring-1 ring-inset">
                {/* Use different colors based on status */}
                <span className="h-1.5 w-1.5 rounded-full bg-green-500 mr-1" />
                Active
              </span>
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
              <span className="inline-flex items-center rounded-full px-2 py-1 text-xs font-medium ring-1 ring-inset">
                <span className="h-1.5 w-1.5 rounded-full bg-red-500 mr-1" />
                Inactive
              </span>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  );
};

export default VehicleReportTable;
