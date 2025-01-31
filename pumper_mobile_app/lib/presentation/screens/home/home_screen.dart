import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:pumper_mobile_app/core/constants/app_assets.dart';
import 'package:pumper_mobile_app/core/constants/app_colors.dart';
import 'package:pumper_mobile_app/data/models/fuel_transaction_model.dart';
import 'package:pumper_mobile_app/presentation/screens/home/home_controller.dart';
import 'package:pumper_mobile_app/presentation/screens/shared/primary_button.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/app_drawer.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/summary_card.dart';
import 'package:pumper_mobile_app/presentation/screens/qr_scanner/qr_scanner_screen.dart';
import 'package:pumper_mobile_app/presentation/screens/home/widgets/fuel_transaction_card.dart';

class HomeScreen extends StatelessWidget {
  HomeScreen({super.key});

  RxList<FuelTransactionModel> transactions = <FuelTransactionModel>[].obs;
  final homeController = HomeController();

  Future<void> _openQRScanner(BuildContext context) async {
    final result = await Navigator.push<String>(
      context,
      MaterialPageRoute(
        builder: (context) => const QRScannerScreen(),
      ),
    );

    if (result != null && context.mounted) {
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
    RxDouble totalFuelAmount = 0.0.obs;
    void getTransactions() async {
      List<FuelTransactionModel> transactionsList =
          await homeController.getFuelTransactions();
      double totalFuelAmountTmp = 0;
      for (var transaction in transactionsList) {
        totalFuelAmountTmp += transaction.pumpedQuantity;
      }
      print(totalFuelAmountTmp);

      totalFuelAmount.value = totalFuelAmountTmp;
      transactions.value = transactionsList;
    }

    getTransactions();
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
              Row(
                children: [
                  Expanded(
                    child: Obx(() {
                      return SummaryCard(
                        title: "Today's Fuel Amount",
                        value: "${totalFuelAmount.value.toStringAsFixed(2)} L",
                        icon: Icons.local_gas_station,
                        color: Colors.blue,
                      );
                    }),
                  ),
                  SizedBox(width: 16),
                  Expanded(
                    child: Obx(() {
                      return SummaryCard(
                        title: "Last Fuel Amount",
                        value: "${transactions.isNotEmpty ? transactions[0].pumpedQuantity.toStringAsFixed(2) : 0} L",
                        icon: Icons.history,
                        color: Colors.green,
                      );
                    }),
                  ),
                ],
              ),

              const SizedBox(height: 16),
              PrimaryButton(
                onPressed: () => _openQRScanner(context),
                text: "Scan QR",
              ),
              const SizedBox(height: 24),
              const Text(
                "Today's Fuel Transactions",
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              Expanded(
                child: Obx(() {
                  return ListView.builder(
                    itemCount: transactions.length,
                    itemBuilder: (context, index) {
                      return FuelTransactionCard(
                        transactionNumber: transactions[index].id,
                        vehicleNumber: transactions[index].vehicleNumber,
                        fuelAmount: transactions[index].pumpedQuantity,
                        timestamp:
                            DateTime.parse(transactions[index].transactionDate),
                      );
                    },
                  );
                }),
              ),
            ],
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _openQRScanner(context),
        backgroundColor: AppColors.primary,
        child: const Icon(Icons.qr_code_scanner),
      ),
    );
  }
}
