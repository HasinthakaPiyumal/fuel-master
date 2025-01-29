import React from 'react';

const NewStation = () => {
    return (
        <div className="new-station">
            <h1>Add New Station</h1>
            <form>
                <div className="form-group">
                    <label>Station Name</label>
                    <input type="text" name="stationName" />
                </div>
                <div className="form-group">
                    <label>Location</label>
                    <input type="text" name="location" />
                </div>
                <button type="submit">Create Station</button>
            </form>
        </div>
    );
};

export default NewStation; 