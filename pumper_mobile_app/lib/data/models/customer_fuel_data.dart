class CustomerFuelData {
  final String id;
  final String name;
  final String vehicleNumber;
  final double totalQuota;
  final double usedQuota;
  final DateTime lastPurchase;
  final String fuelType;
  final String vehicleType;

  CustomerFuelData({
    required this.id,
    required this.name,
    required this.vehicleNumber,
    required this.totalQuota,
    required this.usedQuota,
    required this.lastPurchase,
    required this.fuelType,
    required this.vehicleType,
  });

  double get remainingQuota => totalQuota - usedQuota;
} 