import 'package:flutter/material.dart';
import 'package:pumper_mobile_app/core/constants/app_colors.dart';
import 'package:pumper_mobile_app/core/utils/date_time_util.dart';

class FuelTransactionCard extends StatelessWidget {
  final int transactionNumber;
  final String vehicleNumber;
  final double fuelAmount;
  final DateTime timestamp;

  const FuelTransactionCard({
    super.key,
    required this.transactionNumber,
    required this.vehicleNumber,
    required this.fuelAmount,
    required this.timestamp,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: 8),
      child: ListTile(
        leading: const CircleAvatar(
          backgroundColor: AppColors.primary,
          child: Icon(Icons.local_gas_station, color: Colors.white),
        ),
        title: Text('Transaction #$transactionNumber'),
        subtitle: Text('Vehicle: $vehicleNumber'),


        trailing: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.end,
          children: [
            Text(
              '${fuelAmount.toStringAsFixed(2)} L',
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 16,
              ),
            ),

            Text(
              '${DateTimeUtils.formatTimestamp12Hour(timestamp)}',
              style: const TextStyle(fontSize: 12),
            ),

          ],
        ),
      ),
    );
  }
} 