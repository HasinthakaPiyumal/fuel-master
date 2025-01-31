import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:pumper_mobile_app/domain/entities/fuel_transaction_data.dart';
import 'package:pumper_mobile_app/presentation/screens/home/home_screen.dart';
import 'package:pumper_mobile_app/presentation/screens/shared/app_dialog.dart';
import 'package:pumper_mobile_app/data/repositories/fuel_transaction_repository_impl.dart';

class CustomerQuotaController {
  final TextEditingController fuelQuantityController = TextEditingController();
  final FuelTransactionData _fuelTransactionData;
  final formKey = GlobalKey<FormState>();

  CustomerQuotaController(this._fuelTransactionData);

  Future<void> onSubmit() async {
    print("onSubmit called");
    if (formKey.currentState?.validate() ?? false) {
      try {
        final fuelQuantity = double.tryParse(fuelQuantityController.text);
        print("fuelQuantity: $fuelQuantity");
        if (fuelQuantity == null) {
          return;
        }
        _fuelTransactionData.setFuelQuantity(fuelQuantity);
        Map response = await FuelTransactionRepositoryImpl()
            .createFuelTransaction(_fuelTransactionData);
        if (response['status'] == 200) {
          Get.offAll(() => HomeScreen());
          AppDialog.showSuccess(message: "Fuel transaction created successfully");
        } else {
          AppDialog.showError(message: response['message'] ?? "Fuel transaction creation failed");
        }
      } on Exception catch (e) {
        print(e);
        print("Error in onSubmit");
      }

    }else{
        print("Invalid form"+formKey.currentState.toString());
    }
  }

  String? validateInput(value) {
    final fuelQuantity = double.tryParse(fuelQuantityController.text);
    if (fuelQuantityController.text.isEmpty) {
      return "Please enter a valid fuel quantity";
    } else if (fuelQuantity == null) {
      return "Please enter a valid fuel quantity";
    } else if (fuelQuantity <= 0) {
      return "Fuel quantity cannot be negative or zero";
    } else if (fuelQuantity > _fuelTransactionData.availableFuel) {
      return "You do not have enough fuel quota";
    }
    return null;
  }
}
