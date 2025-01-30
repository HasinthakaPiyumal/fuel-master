import React from "react";
import VehicleTypesTable from "./vehicle-types-table";

const ListVehicleTypes = () => {
  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Stations List</h1>
      <VehicleTypesTable />
    </div>
  );
};

export default ListVehicleTypes;
