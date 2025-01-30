import 'dart:convert';
import 'package:pumper_mobile_app/domain/entities/user.dart';


// ignore_for_file: public_member_api_docs, sort_constructors_first

class UserModel {
  final int id;
  final String name;
  final String phone;
  final String nic;
  final String fuelStationLocation;
  UserModel({
    required this.id,
    required this.name,
    required this.phone,
    required this.nic,
    required this.fuelStationLocation,
  });

  UserModel copyWith({
    int? id,
    String? name,
    String? phone,
    String? nic,
    String? fuelStationLocation,
  }) {
    return UserModel(
      id: id ?? this.id,
      name: name ?? this.name,
      phone: phone ?? this.phone,
      nic: nic ?? this.nic,
      fuelStationLocation: fuelStationLocation ?? this.fuelStationLocation,
    );
  }

  User toEntity() {
    return User(
      id: id,
      name: name,
      phone: phone,
      nic: nic,
      fuelStationLocation: fuelStationLocation,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'name': name,
      'phone': phone,
      'nic': nic,
      'fuelStationLocation': fuelStationLocation,
    };
  }

  factory UserModel.fromMap(Map<String, dynamic> map) {
    return UserModel(
      id: map['id'] as int,
      name: map['name'] as String,
      phone: map['phone'] as String,
      nic: map['nic'] as String,
      fuelStationLocation: map['fuelStation'] as String,
    );
  }

  String toJson() => json.encode(toMap());

  factory UserModel.fromJson(String source) => UserModel.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'UserModel(id: $id, name: $name, phone: $phone, nic: $nic, fuelStationLocation: $fuelStationLocation)';
  }

  @override
  bool operator ==(covariant UserModel other) {
    if (identical(this, other)) return true;
  
    return 
      other.id == id &&
      other.name == name &&
      other.phone == phone &&
      other.nic == nic &&
      other.fuelStationLocation == fuelStationLocation;
  }

  @override
  int get hashCode {
    return id.hashCode ^
      name.hashCode ^
      phone.hashCode ^
      nic.hashCode ^
      fuelStationLocation.hashCode;
  }
}
