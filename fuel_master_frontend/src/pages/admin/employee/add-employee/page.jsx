import React from 'react';

const AddEmployee = () => {
    return (
        <div className="add-employee">
            <h1>Add New Employee</h1>
            <form>
                <div className="form-group">
                    <label>Employee Name</label>
                    <input type="text" name="employeeName" />
                </div>
                <div className="form-group">
                    <label>Employee ID</label>
                    <input type="text" name="employeeId" />
                </div>
                <div className="form-group">
                    <label>Position</label>
                    <input type="text" name="position" />
                </div>
                <button type="submit">Add Employee</button>
            </form>
        </div>
    );
};

export default AddEmployee; 