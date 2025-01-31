import 'dart:convert';
// ignore_for_file: public_member_api_docs, sort_constructors_first

class FuelTransactionModel {
  final int id;
  final String fuelType;
  final double pumpedQuantity;
  final String transactionDate;
  final String vehicleNumber;
  FuelTransactionModel({
    required this.id,
    required this.fuelType,
    required this.pumpedQuantity,
    required this.transactionDate,
    required this.vehicleNumber,
  });

  FuelTransactionModel copyWith({
    int? id,
    String? fuelType,
    double? pumpedQuantity,
    String? transactionDate,
    String? vehicleNumber,
  }) {
    return FuelTransactionModel(
      id: id ?? this.id,
      fuelType: fuelType ?? this.fuelType,
      pumpedQuantity: pumpedQuantity ?? this.pumpedQuantity,
      transactionDate: transactionDate ?? this.transactionDate,
      vehicleNumber: vehicleNumber ?? this.vehicleNumber,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'fuelType': fuelType,
      'pumpedQuantity': pumpedQuantity,
      'transactionDate': transactionDate,
      'vehicleNumber': vehicleNumber,
    };
  }

  factory FuelTransactionModel.fromMap(Map<String, dynamic> map) {
    return FuelTransactionModel(
      id: map['id'] as int,
      fuelType: map['vehicle']['vehicleType']['fuelType'] as String,
      pumpedQuantity: map['pumpedQuantity'] as double,
      transactionDate: map['transactionDate'].toString(),
      vehicleNumber: map['vehicle']['vehicleRegistrationPart1'] +"-"+
          map['vehicle']['vehicleRegistrationPart2'].toString(),
    );
  }


  String toJson() => json.encode(toMap());

  factory FuelTransactionModel.fromJson(String source) =>
      FuelTransactionModel.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'FuelTransactionModel(id: $id, fuelType: $fuelType, pumpedQuantity: $pumpedQuantity, transactionDate: $transactionDate, vehicleNumber: $vehicleNumber)';
  }

  @override
  bool operator ==(covariant FuelTransactionModel other) {
    if (identical(this, other)) return true;

    return other.id == id &&
        other.fuelType == fuelType &&
        other.pumpedQuantity == pumpedQuantity &&
        other.transactionDate == transactionDate &&
        other.vehicleNumber == vehicleNumber;
  }

  @override
  int get hashCode {
    return id.hashCode ^
        fuelType.hashCode ^
        pumpedQuantity.hashCode ^
        transactionDate.hashCode ^
        vehicleNumber.hashCode;
  }
}
