import 'login_controller.dart';
import 'widgets/login_form.dart';
import 'package:flutter/material.dart';
import '../../../core/constants/app_colors.dart';
import '../../../core/constants/app_assets.dart';
import '../../../core/constants/app_text_styles.dart';

class LoginScreen extends StatelessWidget {
  final LoginController controller = LoginController();

  LoginScreen({super.key});
  

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const SizedBox(height: 48),
              Center(
                child: Image.asset(
                  AppAssets.logo,
                  height: 120,
                ),
              ),
              const SizedBox(height: 48),
              Text(
                'Welcome Back',
                style: AppTextStyles.headline1,
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 8),
              Text(
                'Sign in to continue',
                style: AppTextStyles.bodyText2,
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 32),
              LoginForm(controller: controller),
            ],
          ),
        ),
      ),
    );
  }
} 