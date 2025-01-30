import 'package:dio/dio.dart';
import 'package:pumper_mobile_app/core/services/storage_service.dart';

class ApiService {
  final StorageService _storageService = StorageService();
  
  static final ApiService _instance = ApiService._internal();
  static String token = '';

  static const String _baseUrl = 'http://192.168.8.116:8080/api/v1';

  late final Dio _dio;

  Map<String, String> _getHeaders(String endpoint) {
    final headers = {'Content-Type': 'application/json'};
    if (!endpoint.contains('/employee/login')) {
      headers['Authorization'] = 'Bearer $token';
    }
    return headers;
  }

  ApiService._internal() {
    _dio = Dio(
      BaseOptions(
        baseUrl: _baseUrl,
        headers: {'Content-Type': 'application/json'},
      ),
    );
    token = _storageService.getString('token') ?? '';
  }

  factory ApiService() {
    return _instance;
  }

  // Generic GET request
  Future<Map<String, dynamic>> get(String endpoint) async {
    try {
      final response = await _dio.get(
        endpoint,
        options: Options(headers: _getHeaders(endpoint)),
      );
      return response.data;
    } on DioException catch (e) {
      return e.response?.data;
    }
  }

  // Generic POST request
  Future<Map<String, dynamic>> post(
      String endpoint, Map<String, dynamic> body) async {
    try {
      final response = await _dio.post(
        endpoint,
        data: body,
        options: Options(headers: _getHeaders(endpoint)),
      );
      return  response.data;
    } on DioException catch (e) {
      return e.response?.data;
    }
  }

  // Generic PUT request
  Future<Map<String, dynamic>> put(
      String endpoint, Map<String, dynamic> body) async {
    try {
      final response = await _dio.put(
        endpoint,
        data: body,
        options: Options(headers: _getHeaders(endpoint)),
      );
      return response.data;
    } on DioException catch (e) {
      return e.response?.data;
    }
  }

  // Generic DELETE request
  Future<Map<String, dynamic>> delete(String endpoint) async {
    try {
      final response = await _dio.delete(
        endpoint,
        options: Options(headers: _getHeaders(endpoint)),
      );
      return response.data;
    } on DioException catch (e) {
      return e.response?.data;
    }
  }
}
