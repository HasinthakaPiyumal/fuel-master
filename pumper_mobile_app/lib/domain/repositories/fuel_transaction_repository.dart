import 'package:pumper_mobile_app/data/models/fuel_transaction_model.dart';
import 'package:pumper_mobile_app/domain/entities/fuel_transaction_data.dart';

abstract class FuelTransactionRepository {
  Future<void> createFuelTransaction(FuelTransactionData fuelTransactionData);
  Future<List<FuelTransactionModel>> getFuelTransactions();
}
