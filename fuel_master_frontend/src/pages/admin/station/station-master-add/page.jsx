import React from 'react';

const StationMasterAdd = () => {
    return (
        <div className="station-master-add">
            <h1>Add Station Master</h1>
            <form>
                <div className="form-group">
                    <label>Name</label>
                    <input type="text" name="name" />
                </div>
                <div className="form-group">
                    <label>Employee ID</label>
                    <input type="text" name="employeeId" />
                </div>
                <div className="form-group">
                    <label>Contact Number</label>
                    <input type="tel" name="contactNumber" />
                </div>
                <button type="submit">Add Station Master</button>
            </form>
        </div>
    );
};

export default StationMasterAdd; 