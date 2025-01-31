import 'dart:convert';
import 'package:pumper_mobile_app/domain/entities/customer_fuel_data.dart';
// ignore_for_file: public_member_api_docs, sort_constructors_first


class CustomerFuelDataModel {
  final int id;
  final String name;
  final String vehicleNumber;
  final double totalQuota;
  final double usedQuota;
  final DateTime? lastPurchase;
  final String fuelType;

  final String vehicleType;
  CustomerFuelDataModel({
    required this.id,
    required this.name,
    required this.vehicleNumber,
    required this.totalQuota,
    required this.usedQuota,
    this.lastPurchase,
    required this.fuelType,
    required this.vehicleType,
  });

  CustomerFuelDataModel copyWith({
    int? id,
    String? name,
    String? vehicleNumber,
    double? totalQuota,
    double? usedQuota,
    DateTime? lastPurchase,
    String? fuelType,

    String? vehicleType,
  }) {
    return CustomerFuelDataModel(
      id: id ?? this.id,
      name: name ?? this.name,
      vehicleNumber: vehicleNumber ?? this.vehicleNumber,
      totalQuota: totalQuota ?? this.totalQuota,
      usedQuota: usedQuota ?? this.usedQuota,
      lastPurchase: lastPurchase ?? this.lastPurchase,
      fuelType: fuelType ?? this.fuelType,
      vehicleType: vehicleType ?? this.vehicleType,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'name': name,
      'vehicleNumber': vehicleNumber,
      'totalQuota': totalQuota,
      'usedQuota': usedQuota,
      'lastPurchase': lastPurchase?.millisecondsSinceEpoch,
      'fuelType': fuelType,
      'vehicleType': vehicleType,

    };
  }

  factory CustomerFuelDataModel.fromMap(Map<String, dynamic> map) {
    return CustomerFuelDataModel(
      id: map['vehicle']['id'] as int,
      name: (map['vehicle']['user']['firstName'] + " " + map['vehicle']['user']['lastName']) as String,
      vehicleNumber: (map['vehicle']['vehicleRegistrationPart1'] + " " + map['vehicle']['vehicleRegistrationPart2'].toString() )as String,
      totalQuota: double.parse(map['defaultQuota'].toString()),
      usedQuota: double.parse(map['usedQuota'].toString()),
      lastPurchase: map['lastTransaction'] != null ? DateTime.parse(map['lastTransaction'] as String) : null,
      fuelType: map['vehicle']['vehicleType']['fuelType'] as String,
      vehicleType: map['vehicle']['vehicleType']['vehicleType'] as String,


    );
  }

  String toJson() => json.encode(toMap());

  factory CustomerFuelDataModel.fromJson(String source) => CustomerFuelDataModel.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'CustomerFuelDataModel(id: $id, name: $name, vehicleNumber: $vehicleNumber, totalQuota: $totalQuota, usedQuota: $usedQuota, lastPurchase: $lastPurchase, fuelType: $fuelType, vehicleType: $vehicleType)';
  }

  @override
  bool operator ==(covariant CustomerFuelDataModel other) {
    if (identical(this, other)) return true;
  
    return 
      other.id == id &&
      other.name == name &&
      other.vehicleNumber == vehicleNumber &&
      other.totalQuota == totalQuota &&
      other.usedQuota == usedQuota &&
      other.lastPurchase == lastPurchase &&
      other.fuelType == fuelType &&
      other.vehicleType == vehicleType;
  }

  @override
  int get hashCode {
    return id.hashCode ^
      name.hashCode ^
      vehicleNumber.hashCode ^
      totalQuota.hashCode ^
      usedQuota.hashCode ^
      lastPurchase.hashCode ^
      fuelType.hashCode ^
      vehicleType.hashCode;
  }

  CustomerFuelData toEntity() => CustomerFuelData(
        id: id,
        name: name,
        vehicleNumber: vehicleNumber,
        totalQuota: totalQuota,
        usedQuota: usedQuota,
        lastPurchase: lastPurchase,
        fuelType: fuelType,
        vehicleType: vehicleType,
      );

} 
