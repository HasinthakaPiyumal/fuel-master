import 'package:pumper_mobile_app/core/services/api_service.dart';
import 'package:pumper_mobile_app/domain/repositories/user_repository.dart';

class UserRepositoryImpl implements UserRepository {
  final ApiService _apiService = ApiService();

  @override
  Future<Map<String,dynamic>?> getUser() async{
    try {
      final response = await _apiService.get('/employee/me');
      print(response);
      if(response['status']==200){
        return response['data'];
      }
      return null;
    } catch (e) {
      return null;
    }
  }
  
  @override
  Future<Map<String,dynamic>?> signInWithEmailAndPassword(String phoneNumber, String password) async{
    try {
      final response = await _apiService.post('/employee/login', {
        'phone': phoneNumber,
        'password': password,
      });
      if(response['status']==200){
        return response['data'];
      }
      return null;
    } catch (e) {
      return null;
    }
  }
}
