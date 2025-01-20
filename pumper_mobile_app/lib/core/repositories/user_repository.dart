import 'package:pumper_mobile_app/core/models/user.dart';
import 'package:pumper_mobile_app/core/services/api_service.dart';

class UserRepository {
  final ApiService _apiService = ApiService();
  Future<User?> getUser(String token) async {
    try {
      final response = await _apiService.get('/users/authenticate');
      return User.fromMap(response);
    } catch (e) {
      return null;
    }
  }

  Future<User?> signInWithEmailAndPassword(
      String email, String password) async {
    try {
      // final response = await _apiService.post('/auth/login', {
      //     'email': email,
      //     'password': password,
      // });
      print('Signing in with email and password');
      await Future.delayed(Duration(seconds: 2));
      Map<String, dynamic> response = {
        'id': "1",
        'name': 'John Doe',
        'email': 'jogn@pumper.test',
        'photoUrl': '',
      };

      print('Signing in with email and password');
      return User.fromMap(response);
    } catch (e) {
      print(e);
      return null;
    }
  }
}
