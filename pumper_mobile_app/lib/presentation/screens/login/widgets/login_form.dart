import 'package:get/get.dart';
import '../login_controller.dart';
import '../../home/home_screen.dart';
import '../../shared/app_dialog.dart';
import 'package:flutter/material.dart';
import '../../shared/primary_button.dart';
import '../../shared/app_text_field.dart';

class LoginForm extends StatefulWidget {
  final LoginController controller;

  const LoginForm({
    super.key,
    required this.controller,
  });

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final _formKey = GlobalKey<FormState>();
  bool _obscurePassword = true;

  Future<void> _handleLogin() async {
    if (_formKey.currentState?.validate() ?? false) {
      dynamic res = await widget.controller.login();
      if (res) {
        if (mounted) {
          AppDialog.showSuccess(
            message: 'Login successful!',
            onConfirm: () {
              // Navigate to home or do something else
              Get.offAll(() => const HomeScreen());
            },
          );
        }
      } else if (widget.controller.error != null && mounted) {
        AppDialog.showError(
          message: widget.controller.error!,
        );
      }else {
        AppDialog.showError(
          message: 'An error occurred. Please try again later.',
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          AppTextField(
            controller: widget.controller.emailController,
            label: 'Email',
            hint: 'Enter your email',
            prefixIcon: const Icon(Icons.email_outlined),
            keyboardType: TextInputType.emailAddress,
            textInputAction: TextInputAction.next,
            validator: (value) {
              if (value?.isEmpty ?? true) return 'Email is required';
              if (!value!.contains('@')) return 'Enter a valid email';
              return null;
            },
          ),
          const SizedBox(height: 16),
          AppTextField(
            controller: widget.controller.passwordController,
            label: 'Password',
            hint: 'Enter your password',
            isPassword: true,
            prefixIcon: const Icon(Icons.lock_outline),
            textInputAction: TextInputAction.done,
            validator: (value) {
              if (value?.isEmpty ?? true) return 'Password is required';
              if (value!.length < 6) {
                return 'Password must be at least 6 characters';
              }
              return null;
            },
          ),
          const SizedBox(height: 24),
          Obx(() => PrimaryButton(
            text: 'Login',
            onPressed: _handleLogin,
            isLoading: widget.controller.isLoading,
          )),
        ],
      ),
    );
  }

  @override
  void dispose() {
    widget.controller.dispose();
    super.dispose();
  }
} 