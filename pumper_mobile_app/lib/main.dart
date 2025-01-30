import 'package:get/get.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'presentation/theme/app_theme.dart';
import 'core/services/storage_service.dart';
import 'presentation/screens/login/login_screen.dart';
import 'package:pumper_mobile_app/core/services/auth_service.dart';
import 'package:pumper_mobile_app/presentation/screens/home/home_screen.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Initialize services
  final storageService = StorageService();
  await storageService.init();
  final token = storageService.getString('token');
  bool isLoggedIn = false;
  if(token != null){
    final authService = AuthService();
    isLoggedIn = await authService.signInWithToken();
    if(!isLoggedIn){
      storageService.remove('token');
    }
  }
  

  runApp(MyApp(isLoggedIn: isLoggedIn));
}

class MyApp extends StatelessWidget {
  final bool isLoggedIn;
  const MyApp({super.key, required this.isLoggedIn});

  @override
  Widget build(BuildContext context) {
    SystemChrome.setSystemUIOverlayStyle(
    const SystemUiOverlayStyle(
      statusBarColor: Colors.transparent,
      statusBarIconBrightness: Brightness.dark,
    ),
  );
    return GetMaterialApp(
      title: 'Fuel Master',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.light,
      defaultTransition: Transition.rightToLeftWithFade,
      home: isLoggedIn ?const HomeScreen() : LoginScreen(),
    );
  }
}
