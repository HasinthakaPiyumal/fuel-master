import React from "react";
import StationTable from "./station-table";

const StationList = () => {
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Stations List</h1>
      <StationTable />
    </div>
  );
};

export default StationList;
