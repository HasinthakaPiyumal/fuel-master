import 'package:pumper_mobile_app/domain/entities/user.dart';
import 'package:pumper_mobile_app/data/models/user_model.dart';
import 'package:pumper_mobile_app/core/services/storage_service.dart';
import 'package:pumper_mobile_app/data/repositories/user_repository_impl.dart';

class AuthService {
  // Singleton instance
  static final AuthService _instance =
      AuthService._internal(UserRepositoryImpl(), StorageService());
  final UserRepositoryImpl _userRepository;
  final StorageService _storageService;

  // Factory constructor
  factory AuthService() {
    return _instance;
  }

  // Private constructor
  AuthService._internal(this._userRepository, this._storageService);

  // Current user
  User? _currentUser;

  // Getter for current user
  User? get currentUser => _currentUser;

  // Check if user is authenticated
  bool get isAuthenticated => _currentUser != null;

  // Sign in with email and password
  Future<bool> signInWithEmailAndPassword(
    String phoneNumber,
    String password,
  ) async {
    Map<String, dynamic>? user =
        await _userRepository.signInWithEmailAndPassword(phoneNumber, password);
    if (user != null) {
      UserModel userModel = UserModel.fromMap(user['user']);
      _currentUser = userModel.toEntity();
      await _storageService.setString('token', user['token']);
	  print(user['token']);
	  print(_storageService.getString('token'));
    }
    return user != null;
  }

  Future<bool> signInWithToken() async {
    Map<String, dynamic>? user = await _userRepository.getUser();
    if (user != null) {
      UserModel userModel = UserModel.fromMap(user['user']);
      _currentUser = userModel.toEntity();
      await _storageService.setString('token', user['token']);
    }
    return user != null;
  }

  Future<bool> signOut() async {
    await _storageService.remove('token');
    _currentUser = null;
    return true;
  }
}
