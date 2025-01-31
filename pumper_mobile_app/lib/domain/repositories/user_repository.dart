import 'package:pumper_mobile_app/domain/entities/user.dart';


abstract class UserRepository {
  Future<Map<String,dynamic>?> getUser();
  Future<Map<String,dynamic>?> signInWithEmailAndPassword(String phoneNumber, String password);
}