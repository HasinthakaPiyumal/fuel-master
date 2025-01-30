import React from 'react';
import EmployeeTable from './employee-table';

const ListEmployee = () => {
    return (
        <div className="list-employee">
            <h1 className="text-2xl font-bold text-gray-800 mb-6 border-b pb-4">Employee List</h1>
            <EmployeeTable />
        </div>
    );
};


export default ListEmployee; 