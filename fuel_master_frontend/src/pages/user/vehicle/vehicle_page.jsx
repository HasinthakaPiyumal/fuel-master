import React from 'react';
import { Card, CardContent } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Button } from "@/components/ui/button";
import QRCode from 'qrcode';
import { useState, useEffect } from 'react';
import apiService from "@/services/api.service";
import { showToast } from "@/hooks/use-toast";

export default function VehiclePage() {
  const [vehicleData, setVehicleData] = useState(null);
  const [quotaData, setQuotaData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No token found');
        }

        
        const vehicleResponse = await apiService.get('/vehicle/get');
        setVehicleData(vehicleResponse.data.vehicle);

        
        const quotaResponse = await apiService.get('/api/quota/summary/1');
        setQuotaData(quotaResponse.data);
        
      } catch (error) {
        console.error('Error fetching data:', error);
        showToast.error('Failed to load vehicle or quota data');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleQRDownload = async () => {
    try {
      const qrData = {
        vehicleNumber: vehicleData.vehicleNumber,
        nic: vehicleData.nic,
        fuelType: vehicleData.fuelType,
        quota: quotaData.availableQuota,
        name: vehicleData.name,
        phoneNumber: vehicleData.phoneNumber
      };

      const qrCodeURL = await QRCode.toDataURL(JSON.stringify(qrData));

      const link = document.createElement('a');
      link.href = qrCodeURL;
      link.download = `QR_${vehicleData.vehicleNumber}.png`;
      
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

    } catch (error) {
      console.error('Error generating QR code:', error);
      showToast.error('Failed to generate QR code');
    }
  };

  if (loading) return <div className="text-center text-gray-600">Loading...</div>;
  if (!vehicleData || !quotaData) return <div className="text-center text-red-500">Failed to load data.</div>;

  return (
    <div className="container mx-auto p-4 max-w-6xl">
      <div className="grid md:grid-cols-2 gap-x-20 gap-y-6 justify-center">
        <Card className="bg-white shadow-lg">
          <CardContent className="p-6">
            <h2 className="text-2xl font-semibold text-orange-600 mb-6">Your Vehicle</h2>
            <div className="space-y-4">
              <InfoRow label="Name:" value={vehicleData.name} />
              <InfoRow label="NIC:" value={vehicleData.nic} />
              <InfoRow label="Phone Number:" value={vehicleData.phoneNumber} />
              <InfoRow label="Vehicle Number:" value={vehicleData.vehicleNumber} />
              <InfoRow label="Vehicle Type:" value={vehicleData.vehicleType} />
              <InfoRow label="Chassis Number:" value={vehicleData.chassisNumber} />
              <InfoRow label="Fuel Type:" value={vehicleData.fuelType} />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-white shadow-lg">
          <CardContent className="p-6">
            <h2 className="text-2xl font-semibold text-orange-600 mb-6">Quota Summary</h2>
            <div className="space-y-4">
              <InfoRow label="Available Quota:" value={`${quotaData.availableQuota} L`} />
              <InfoRow label="Quota Used:" value={`${quotaData.quotaUsed} L`} />
              <InfoRow label="Total Quota:" value={`${quotaData.totalQuota} L`} />
              <InfoRow label="Renewal Date:" value={quotaData.renewalDate} />

              <div className="mt-6">
                <Progress 
                  value={(quotaData.quotaUsed / quotaData.totalQuota) * 100} 
                  className="h-2 bg-gray-200"
                />
                <div className="text-right text-sm text-gray-600 mt-1">
                  {quotaData.quotaUsed}L / {quotaData.totalQuota}L
                </div>
              </div>
              <Button 
                className="w-full mt-4 bg-orange-600 hover:bg-orange-700 text-white"
                onClick={handleQRDownload}
              >
                Download QR
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

function InfoRow({ label, value }) {
  return (
    <div className="flex justify-between items-center py-2 border-b border-gray-100">
      <span className="text-gray-600">{label}</span>
      <span className="font-medium">{value}</span>
    </div>
  );
}


