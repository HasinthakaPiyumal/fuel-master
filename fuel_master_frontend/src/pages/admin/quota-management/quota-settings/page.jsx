import React from 'react';

const QuotaSettings = () => {
    return (
        <div className="quota-settings">
            <h1>Quota Settings</h1>
            <form>
                <div className="form-group">
                    <label>Default Station Quota</label>
                    <input type="number" name="defaultQuota" />
                </div>
                <button type="submit">Save Settings</button>
            </form>
        </div>
    );
};

export default QuotaSettings; 