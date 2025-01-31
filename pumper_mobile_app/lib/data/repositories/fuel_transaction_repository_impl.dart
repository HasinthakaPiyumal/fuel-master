import 'package:pumper_mobile_app/core/services/api_service.dart';
import 'package:pumper_mobile_app/data/models/fuel_transaction_model.dart';
import 'package:pumper_mobile_app/domain/entities/fuel_transaction_data.dart';
import 'package:pumper_mobile_app/domain/repositories/fuel_transaction_repository.dart';

class FuelTransactionRepositoryImpl implements FuelTransactionRepository {
  final ApiService _apiService = ApiService();
  @override
  Future<Map<String, dynamic>> createFuelTransaction(
      FuelTransactionData fuelTransactionData) async {
    final data = {
      'vehicleId': fuelTransactionData.vehicleId,
      'pumpedQuantity': fuelTransactionData.fuelQuantity,
    };
    print("data: $data");
    final response = await _apiService.post('/transactions/process', data);
    return response;
  }

  @override
  Future<List<FuelTransactionModel>> getFuelTransactions() async {
    final response = await _apiService.get('/transactions/employee/today');
    List<FuelTransactionModel> transactions = [];
    for (var transaction in response['data']) {
      print("transaction: $transaction");
      transactions.add(FuelTransactionModel.fromMap(transaction));
    }
    return transactions;

  }
}
