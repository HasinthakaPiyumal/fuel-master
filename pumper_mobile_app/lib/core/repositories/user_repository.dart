import 'package:pumper_mobile_app/core/models/user.dart';
import 'package:pumper_mobile_app/core/services/api_service.dart';

class UserRepository {
    final ApiService _apiService = ApiService();
    Future<User> getUser(String token) async {
        try {
            final response = await _apiService.get('/authenticate/user');
            return User.fromMap(response);
        } catch (e) {
            rethrow;
        }
    }
}