import React from 'react';

const EmployeeTable = () => {
    return (
        <div className="employee-table">
            <table>
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>Name</th>
                        <th>Position</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {/* Add table rows here */}
                </tbody>
            </table>
        </div>
    );
};

export default EmployeeTable; 