import 'package:pumper_mobile_app/core/services/api_service.dart';
import 'package:pumper_mobile_app/domain/repositories/qr_repository.dart';

class QrRepositoryImpl implements QrRepository {
  final ApiService _apiService = ApiService();

  @override
  Future<Map<String, dynamic>?> getCustomerFuelData(String qrData) async {
    final response = await _apiService.get('/transactions/info/$qrData');
    return response;
  }
}


