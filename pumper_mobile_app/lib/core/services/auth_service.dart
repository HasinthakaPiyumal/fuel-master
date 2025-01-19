import '../models/user.dart';

class AuthService {
  // Singleton instance
  static final AuthService _instance = AuthService._internal();
  
  // Factory constructor
  factory AuthService() {
    return _instance;
  }
  
  // Private constructor
  AuthService._internal();
  
  // Current user
  User? _currentUser;
  
  // Getter for current user
  User? get currentUser => _currentUser;
  
  // Check if user is authenticated
  bool get isAuthenticated => _currentUser != null;
}
