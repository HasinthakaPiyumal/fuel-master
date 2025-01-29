import React from 'react';

const StationMasterAssign = () => {
    return (
        <div className="station-master-assign">
            <h1>Assign Station Master</h1>
            <form>
                <div className="form-group">
                    <label>Station</label>
                    <select name="station">
                        <option value="">Select Station</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Station Master</label>
                    <select name="stationMaster">
                        <option value="">Select Station Master</option>
                    </select>
                </div>
                <button type="submit">Assign</button>
            </form>
        </div>
    );
};

export default StationMasterAssign; 