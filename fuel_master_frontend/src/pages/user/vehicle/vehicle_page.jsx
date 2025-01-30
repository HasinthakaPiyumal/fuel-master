import React from 'react';
import { Card, CardContent } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Button } from "@/components/ui/button";
import QRCode from 'qrcode';

export default function VehiclePage() {
 
  const vehicleData = {
    name: "Navon Sanjuni",
    nic: "200173600804",
    phoneNumber: "0123456789",
    vehicleNumber: "ABC 1234",
    vehicleType: "Car",
    chassisNumber: "ABCABC1234...",
    fuelType: "Diesel"
  };

  const quotaData = {
    availableQuota: 5,
    quotaUsed: 10,
    totalQuota: 15,
    renewalDate: "2025/01/16 12:22PM"
  };

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
    
    }
  };

  return (
    <div className="container mx-auto p-4 max-w-6xl">
      <div className="grid md:grid-cols-2 gap-6">
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