class FuelTransactionData {
  final String qrId;
  final int vehicleId;
  final String fuelType;
  double fuelQuantity;
  final String vehicleNumber;
  final double availableFuel;



  FuelTransactionData({
    required this.qrId,
    required this.vehicleId,
    required this.fuelType,
    required this.fuelQuantity,
    required this.vehicleNumber,
    required this.availableFuel,
  });

  void setFuelQuantity(double fuelQuantity) {
    this.fuelQuantity = fuelQuantity;
  }

}

