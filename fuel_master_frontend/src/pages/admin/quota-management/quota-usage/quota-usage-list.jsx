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

 
           
};

export default QuotaUsageList; 