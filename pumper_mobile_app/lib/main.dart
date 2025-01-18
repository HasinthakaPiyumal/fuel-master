import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'screens/sign_in_screen.dart';
import 'providers/auth_provider.dart';
import 'providers/fuel_provider.dart';
import 'providers/qr_code_provider.dart';

void main() {
  runApp(const FuelQuotaApp());
}

class FuelQuotaApp extends StatelessWidget {
  const FuelQuotaApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthProvider()),
        ChangeNotifierProvider(create: (_) => FuelProvider()),
        ChangeNotifierProvider(create: (_) => QRCodeProvider()),
      ],
      child: MaterialApp(
        title: 'Fuel Quota App',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(
            seedColor: Colors.blue,
            primary: Colors.blue,
            secondary: Colors.blueAccent,
          ),
          useMaterial3: true,
          appBarTheme: const AppBarTheme(
            backgroundColor: Colors.blue,
            foregroundColor: Colors.white,
            elevation: 2,
          ),
        ),
        home: const SignInScreen(),
        // Define your routes here
        routes: {
          '/sign-in': (context) => const SignInScreen(),
          // Add more routes as needed
        },
      ),
    );
  }
}
