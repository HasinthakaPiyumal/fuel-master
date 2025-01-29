import React from 'react';

const AddVehicleTypes = () => {
    return (
        <div className="add-vehicle-types">
            <h1>Add Vehicle Type</h1>
            <form>
                <div className="form-group">
                    <label>Type Name</label>
                    <input type="text" name="typeName" />
                </div>
                <div className="form-group">
                    <label>Description</label>
                    <textarea name="description"></textarea>
                </div>
                <div className="form-group">
                    <label>Capacity</label>
                    <input type="number" name="capacity" />
                </div>
                <button type="submit">Add Vehicle Type</button>
            </form>
        </div>
    );
};

export default AddVehicleTypes; 