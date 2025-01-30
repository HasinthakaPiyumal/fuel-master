import 'package:get/get.dart';
import 'package:flutter/material.dart';
import '../../../core/services/auth_service.dart';
import '../../../core/services/storage_service.dart';

class LoginController extends GetxController {
  final AuthService _authService = AuthService();
  final StorageService _storageService = StorageService();
  
  final phoneNumberController = TextEditingController();
  final passwordController = TextEditingController();
  
  final _isLoading = false.obs;
  bool get isLoading => _isLoading.value;
  
  String? _error;
  String? get error => _error;

  Future<bool> login() async {
    if (!_validateInput()) return false;

    _isLoading.value = true;
    _error = null;

    try {
      final phoneNumber = phoneNumberController.text.trim();
      final password = passwordController.text.trim();
      
      final success = await _authService.signInWithEmailAndPassword(
        phoneNumber,
        password,
      );

      if (!success) {
        _error = 'Invalid phone number or password';
      }
      _isLoading.value = false;
      return success;
    } catch (e) {
      _isLoading.value = false;
      _error = e.toString();
      return false;
    }
  }

  bool _validateInput() {
    if (phoneNumberController.text.isEmpty || passwordController.text.isEmpty) {
      _error = 'Please fill in all fields';
      return false;
    }
    
    if (phoneNumberController.text.length < 10) {
      _error = 'Please enter a valid phone number';
      return false;
    }
    
    if (passwordController.text.length < 6) {
      _error = 'Password must be at least 6 characters';
      return false;
    }
    
    return true;
  }

  void dispose() {
    phoneNumberController.dispose();
    passwordController.dispose();
  }
} 