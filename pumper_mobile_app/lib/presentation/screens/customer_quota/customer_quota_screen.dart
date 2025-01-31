import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:pumper_mobile_app/domain/entities/customer_fuel_data.dart';
import 'package:pumper_mobile_app/domain/entities/fuel_transaction_data.dart';
import 'package:pumper_mobile_app/presentation/screens/customer_quota/customer_quota_controller.dart';

class CustomerQuotaScreen extends StatelessWidget {
  final CustomerFuelData customerData;
  final String qrId;

  const CustomerQuotaScreen({
    super.key,
    required this.customerData,
    required this.qrId,
  });

  @override
  Widget build(BuildContext context) {
    final controller = CustomerQuotaController(FuelTransactionData(
      qrId: qrId,
      vehicleId: customerData.id,
      fuelType: customerData.fuelType,
      fuelQuantity: 0,
      vehicleNumber: customerData.vehicleNumber,
      availableFuel: customerData.totalQuota! - customerData.usedQuota!,
    ));
    return Scaffold(
      appBar: AppBar(
        title: const Text('Customer Fuel Quota'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: controller.formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _buildCustomerInfo(),
              const SizedBox(height: 24),
              _buildQuotaCard(),
              const SizedBox(height: 24),
              _buildFuelEntrySection(controller: controller),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildCustomerInfo() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const CircleAvatar(
                  radius: 24,
                  backgroundColor: Colors.blue,
                  child: Icon(
                    Icons.person,
                    color: Colors.white,
                    size: 32,
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        customerData.name ?? '',
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        customerData.vehicleNumber ?? '',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            const Divider(),
            const SizedBox(height: 16),
            Row(
              children: [
                _buildInfoItem(
                  icon: Icons.local_gas_station,
                  label: 'Fuel Type',
                  value: customerData.fuelType,
                  color: Colors.blue,
                ),
                const SizedBox(width: 24),
                _buildInfoItem(
                  icon: Icons.directions_car,
                  label: 'Vehicle Type',
                  value: customerData.vehicleType,
                  color: Colors.green,
                ),
              ],
            ),
            Text(
              'Last Purchase: ${customerData.lastPurchase!=null?_formatDate(customerData.lastPurchase!):"N/A"}',
              style: TextStyle(
                color: Colors.grey[600],
              ),
            ),

          ],
        ),
      ),
    );
  }

  Widget _buildQuotaCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Fuel Quota Status',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            _buildQuotaProgressBar(),
            const SizedBox(height: 24),
            _buildQuotaDetails(),
          ],
        ),
      ),
    );
  }

  Widget _buildQuotaProgressBar() {
    final percentage =
        (customerData.usedQuota! / customerData.totalQuota! * 100)
            .clamp(0, 100)
            .toDouble();

    return Column(
      children: [
        LinearProgressIndicator(
          value: percentage / 100,
          minHeight: 10,
          backgroundColor: Colors.grey[200],
          valueColor: AlwaysStoppedAnimation<Color>(
            percentage >= 90 ? Colors.red : Colors.blue,
          ),
        ),
        const SizedBox(height: 8),
        Text(
          '${percentage.toStringAsFixed(1)}% Used',
          style: TextStyle(
            color: Colors.grey[600],
            fontSize: 12,
          ),
        ),
      ],
    );
  }

  Widget _buildQuotaDetails() {
    return Row(
      children: [
        _buildQuotaItem(
          'Total Quota',
          customerData.totalQuota ?? 0,
          Colors.blue,
        ),
        _buildQuotaItem(
          'Used',
          customerData.usedQuota ?? 0,
          Colors.orange,
        ),
        _buildQuotaItem(
          'Remaining',
          customerData.totalQuota! - customerData.usedQuota!,
          Colors.green,
        ),
      ],
    );
  }

  Widget _buildQuotaItem(String label, double amount, Color color) {
    return Expanded(
      child: Column(
        children: [
          Text(
            label,
            style: TextStyle(
              color: Colors.grey[600],
              fontSize: 12,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            '${amount.toStringAsFixed(1)}L',
            style: TextStyle(
              color: color,
              fontSize: 16,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildFuelEntrySection({required CustomerQuotaController controller}) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text(
              'New Fuel Entry',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            TextFormField(
              controller: controller.fuelQuantityController,
              keyboardType: TextInputType.number,
              validator: controller.validateInput,
              autovalidateMode: AutovalidateMode.onUserInteraction,
              decoration: const InputDecoration(
                labelText: 'Enter Fuel Amount (L)',
                border: OutlineInputBorder(),
                suffixText: 'L',
              ),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                controller.onSubmit();
              },
              child: const Text('Confirm Fuel Entry'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoItem({
    required IconData icon,
    required String label,
    required String value,
    required Color color,
  }) {
    return Expanded(
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: color.withOpacity(0.1),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Icon(
              icon,
              color: color,
              size: 20,
            ),
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: TextStyle(
                    color: Colors.grey[600],
                    fontSize: 12,
                  ),
                ),
                Text(
                  value,
                  style: const TextStyle(
                    fontWeight: FontWeight.w500,
                    fontSize: 14,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  String _formatDate(DateTime date) {
    return '${date.day}/${date.month}/${date.year} ${date.hour}:${date.minute}';
  }
}
