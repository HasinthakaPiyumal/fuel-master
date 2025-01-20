import 'package:flutter/material.dart';
import 'package:pumper_mobile_app/core/constants/app_assets.dart';
import 'package:pumper_mobile_app/core/constants/app_colors.dart';
import 'package:pumper_mobile_app/presentation/screens/shared/primary_button.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/app_drawer.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/summary_card.dart';
import 'package:pumper_mobile_app/presentation/screens/qr_scanner/qr_scanner_screen.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/fuel_transaction_card.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  Future<void> _openQRScanner(BuildContext context) async {
    final result = await Navigator.push<String>(
      context,
      MaterialPageRoute(
        builder: (context) => const QRScannerScreen(),
      ),
    );

    if (result != null && context.mounted) {
      // Handle the scanned QR code result
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Scanned QR Code: $result'),
          behavior: SnackBarBehavior.floating,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        backgroundColor: AppColors.background,
        elevation: 0,
        actions: [
          Builder(
            builder: (context) => IconButton(
              icon: const Icon(Icons.menu, color: Colors.black),
              onPressed: () {
                Scaffold.of(context).openDrawer();
              },
            ),
          )
        ],
        leading: Padding(
          padding: const EdgeInsets.only(left: 16.0),
          child: Image.asset(
            AppAssets.logo,
            height: 40,
            fit: BoxFit.contain,
          ),
        ),
        leadingWidth: 100,
        centerTitle: false,
      ),
      drawer: const AppDrawer(),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(height: 24),

              // Summary Cards Row
              Row(
                children: const [
                  Expanded(
                    child: SummaryCard(
                      title: "Today's Fuel Amount",
                      value: "1,250 L",
                      icon: Icons.local_gas_station,
                      color: Colors.blue,
                    ),
                  ),
                  SizedBox(width: 16),
                  Expanded(
                    child: SummaryCard(
                      title: "Last Fuel Amount",
                      value: "980 L",
                      icon: Icons.history,
                      color: Colors.green,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 16),
              PrimaryButton(
                onPressed: () => _openQRScanner(context),
                text: "Scan QR",
              ),
              const SizedBox(height: 24),

              // Today's Fuel List Title
              const Text(
                "Today's Fuel Transactions",
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),

              // Today's Fuel List
              Expanded(
                child: ListView.builder(
                  itemCount: 5, // Replace with actual data length
                  itemBuilder: (context, index) {
                    return FuelTransactionCard(
                      transactionNumber: index + 1,
                      vehicleNumber: 'ABC-${1234 + index}',
                      fuelAmount: 150 + (index * 50),
                      timestamp: DateTime.now(),
                    );
                  },
                ),
              ),
            ],
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _openQRScanner(context),
        backgroundColor: Colors.blue,
        child: const Icon(Icons.qr_code_scanner),
      ),
    );
  }
}
