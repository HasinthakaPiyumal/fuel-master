import React, { useState, useEffect } from 'react';
import { FaEdit, FaTrash } from 'react-icons/fa';

const QuotaUsageList = () => {
    const [quotaData, setQuotaData] = useState([
        {
            id: 1,
            stationName: 'Station A',
            quotaUsed: 1500,
            quotaLimit: 2000,
            usagePeriod: '2024-03',
            lastUpdated: '2024-03-15',
            status: 'Active',
            remainingQuota: 500,
            utilizationPercentage: 75
        },
        {
            id: 2,
            stationName: 'Station B',
            quotaUsed: 800,
            quotaLimit: 1500,
            usagePeriod: '2024-03',
            lastUpdated: '2024-03-15',
            status: 'Active',
            remainingQuota: 700,
            utilizationPercentage: 53
        },
        {
            id: 3,
            stationName: 'Station C',
            quotaUsed: 1800,
            quotaLimit: 1800,
            usagePeriod: '2024-03',
            lastUpdated: '2024-03-15',
            status: 'Inactive',
            remainingQuota: 600,
            utilizationPercentage: 67
        },
        {
            id: 4,
            stationName: 'Station D',
            quotaUsed: 950,
            quotaLimit: 1000,
            usagePeriod: '2024-03',
            lastUpdated: '2024-03-15',
            status: 'Active',
            remainingQuota: 50,
            utilizationPercentage: 95
        }
    ]);

   
    const [searchTerm, setSearchTerm] = useState('');
    const [statusFilter, setStatusFilter] = useState('');

  
    const handleEdit = (id) => {
       
        console.log('Edit clicked for ID:', id);
    };

    
    const handleDelete = (id) => {
        if (window.confirm('Are you sure you want to delete this record?')) {
            setQuotaData(quotaData.filter(item => item.id !== id));
        }
    };

   
    const filteredData = quotaData.filter(item => {
        const matchesSearch = item.stationName.toLowerCase().includes(searchTerm.toLowerCase());
        const matchesStatus = statusFilter === '' || item.status === statusFilter;
        return matchesSearch && matchesStatus;
    });

    return (
        <div className="p-6">


            <div className="mb-6 flex gap-4">
                <input
                    type="text"
                    placeholder="Search by station name..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="p-2 border rounded-md w-64"
                />
                <select
                    value={statusFilter}
                    onChange={(e) => setStatusFilter(e.target.value)}
                    className="p-2 border rounded-md"
                >
                    <option value="">All Status</option>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                </select>
            </div>

           
            <div className="overflow-x-auto shadow-md rounded-lg">
                <table className="min-w-full bg-white">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="p-3 text-left font-semibold">Station</th>
                            <th className="p-3 text-left font-semibold">Quota Used (L)</th>
                            <th className="p-3 text-left font-semibold">Quota Limit (L)</th>
                            <th className="p-3 text-left font-semibold">Usage Period</th>
                            <th className="p-3 text-left font-semibold">Remaining Quota (L)</th>
                            <th className="p-3 text-left font-semibold">Last Updated</th>
                            <th className="p-3 text-left font-semibold">Status</th>
                            <th className="p-3 text-left font-semibold">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {filteredData.map((row) => (
                            <tr key={row.id} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap">{row.stationName}</td>
                                <td className="px-6 py-4 whitespace-nowrap">{row.quotaUsed.toLocaleString()}</td>
                                <td className="px-6 py-4 whitespace-nowrap">{row.quotaLimit.toLocaleString()}</td>
                                <td className="px-6 py-4 whitespace-nowrap">{row.usagePeriod}</td>
                                <td className="px-6 py-4 whitespace-nowrap">{row.remainingQuota.toLocaleString()}</td>

                                <td className="px-6 py-4 whitespace-nowrap">{row.lastUpdated}</td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    <span className={`px-2 py-1 text-sm rounded-full ${row.status === 'Active'
                                            ? 'bg-green-100 text-green-800'
                                            : 'bg-red-100 text-red-800'
                                        }`}>
                                        {row.status}
                                    </span>
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap">
                                    <div className="flex space-x-3">
                                        <button
                                            onClick={() => handleEdit(row.id)}
                                            className="text-blue-600 hover:text-blue-900"
                                        >
                                            <FaEdit className="h-5 w-5" />
                                        </button>
                                        <button
                                            onClick={() => handleDelete(row.id)}
                                            className="text-red-600 hover:text-red-900"
                                        >
                                            <FaTrash className="h-5 w-5" />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>


            {filteredData.length === 0 && !loading && (
                <div className="text-center py-4 text-gray-500">
                    No records found
                </div>
            )}
        </div>
    );
};

export default QuotaUsageList; 