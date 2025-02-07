import { Card, CardContent } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Button } from "@/components/ui/button";
import QRCode from 'qrcode';
import apiService from "@/services/api.service";
import { showToast } from "@/hooks/use-toast";
import { useQuery } from "@tanstack/react-query";
import PropTypes from 'prop-types';
export default function VehiclePage() {


  const { data: fetchedVehicleData, loading } = useQuery({
    queryKey: ['vehicle'],
    queryFn: () => apiService.get('/user/vehicle')
  })

  const vehicleData = fetchedVehicleData?.data?.data;

  const handleQRDownload = async () => {
    try {
      const qrData = {
        qrId: vehicleData.vehicle.qrId,
        numberPlate: vehicleData.vehicle.vehicleRegistrationPart1 + " " + vehicleData.vehicle.vehicleRegistrationPart2,
      };

      const qrCodeURL = await QRCode.toDataURL(JSON.stringify(qrData));

      const link = document.createElement('a');
      link.href = qrCodeURL;
      link.download = `QR_${vehicleData.vehicle.vehicleRegistrationPart1 + " " + vehicleData.vehicle.vehicleRegistrationPart2}.png`;

      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

    } catch (error) {
      console.error('Error generating QR code:', error);
      showToast.error('Failed to generate QR code');
    }
  };

  if (loading) return <div className="text-center text-gray-600">Loading...</div>;
  if (!vehicleData) return <div className="text-center text-red-500">Failed to load data.</div>;


  return (
    <div className="container mx-auto p-4 max-w-6xl">
      <div className="grid md:grid-cols-2 gap-x-20 gap-y-6 justify-center">
        <Card className="bg-white shadow-lg">
          <CardContent className="p-6">
            <h2 className="text-2xl font-semibold text-orange-600 mb-6">Your Vehicle</h2>
            <div className="space-y-4">
              <InfoRow label="Name:" value={vehicleData.user.firstName + " " + vehicleData.user.lastName} />
              <InfoRow label="NIC:" value={vehicleData.user.nic} />
              <InfoRow label="Phone Number:" value={vehicleData.user.phone} />
              <InfoRow label="Vehicle Number:" value={vehicleData.vehicle.vehicleRegistrationPart1 + "-" + vehicleData.vehicle.vehicleRegistrationPart2} />
              <InfoRow label="Vehicle Type:" value={vehicleData.vehicle.vehicleType.vehicleType} />
              <InfoRow label="Chassis Number:" value={vehicleData.vehicle.chassisNumber} />
              <InfoRow label="Fuel Type:" value={vehicleData.vehicle.vehicleType.fuelType} />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-white shadow-lg">
          <CardContent className="p-6">
            <h2 className="text-2xl font-semibold text-orange-600 mb-6">Quota Summary</h2>
            <div className="space-y-4">
              <InfoRow label="Available Quota:" value={`${vehicleData.availableQuota} L`} />
              <InfoRow label="Quota Used:" value={`${vehicleData.usedQuota} L`} />
              <InfoRow label="Total Quota:" value={`${vehicleData.defaultQuota} L`} />
              <InfoRow label="Renewal Date:" value={vehicleData.renewalDate} />

              <div className="mt-6">
                <Progress
                  value={(vehicleData.usedQuota / vehicleData.defaultQuota) * 100}
                  className="h-2 bg-gray-200"
                />
                <div className="text-right text-sm text-gray-600 mt-1">
                  {vehicleData.usedQuota}L / {vehicleData.defaultQuota}L
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

InfoRow.propTypes = {
  label: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired
};

