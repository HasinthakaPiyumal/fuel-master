import React from "react";
import VehicleReportTable from "./vehicle-report-table";

const VehicleReport = () => {
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Vehicle Reports</h1>
      <VehicleReportTable />
    </div>
  );
};

export default VehicleReport;
