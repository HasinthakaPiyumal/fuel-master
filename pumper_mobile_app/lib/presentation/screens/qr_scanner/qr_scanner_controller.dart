import 'dart:convert';
import 'package:get/get.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:pumper_mobile_app/data/models/customer_fuel_data_model.dart';
import 'package:pumper_mobile_app/data/repositories/qr_repository_impl.dart';
import 'package:pumper_mobile_app/presentation/screens/shared/app_dialog.dart';
import 'package:pumper_mobile_app/presentation/screens/customer_quota/customer_quota_screen.dart';

class QrScannerController extends GetxController {
  static int detectCount = 0;
  void onDetect(
      MobileScannerController controller, BarcodeCapture capture) async {
    // controller.pause();
    detectCount++;
    print("=================== Detect Count: $detectCount ===================");
    final List<Barcode> barcodes = capture.barcodes;

    for (final barcode in barcodes) {
      if (barcode.rawValue == null) {
        controller.start();
        continue;
      } else {
        try {
          final qrRepository = QrRepositoryImpl();
          Map<String, dynamic> qrData = jsonDecode(barcode.rawValue!);

          if (qrData['qrId'] == null) {
            controller.start();
            continue;
          }          
          controller.pause();
          Map<String, dynamic>? response =
              await qrRepository.getCustomerFuelData(qrData['qrId']);


          if (response != null && response['status'] == 200) {
            CustomerFuelDataModel customerFuelData =
                CustomerFuelDataModel.fromMap(response['data']);
            controller.dispose();
            Get.off(() =>
                CustomerQuotaScreen(customerData: customerFuelData.toEntity(),qrId: qrData['qrId']));
          } else {
            Get.back();
            AppDialog.showError(
                message: response!['message'] ?? 'Something went wrong');
          }

        } on Exception catch (e) {
          print("=================== Error ===================");
          print(e);
          //   controller.start();
        }
      }

      break;
    }
  }
}
