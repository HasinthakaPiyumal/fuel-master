import 'package:get/get.dart';
import 'package:pumper_mobile_app/data/models/fuel_transaction_model.dart';
import 'package:pumper_mobile_app/domain/repositories/fuel_transaction_repository.dart';
import 'package:pumper_mobile_app/data/repositories/fuel_transaction_repository_impl.dart';

class HomeController {
  final FuelTransactionRepository fuelTransactionRepository = FuelTransactionRepositoryImpl();

  Future<List<FuelTransactionModel>> getFuelTransactions() async {
    final transactions = await fuelTransactionRepository.getFuelTransactions();
    return transactions.reversed.toList();
  }

}