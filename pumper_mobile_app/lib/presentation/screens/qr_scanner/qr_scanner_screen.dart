import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:pumper_mobile_app/presentation/screens/qr_scanner/qr_scanner_controller.dart';

class QRScannerScreen extends StatefulWidget {
  const QRScannerScreen({super.key});

  @override
  State<QRScannerScreen> createState() => _QRScannerScreenState();
}

class _QRScannerScreenState extends State<QRScannerScreen> {
  late MobileScannerController controller;
  final QrScannerController qrScannerController = QrScannerController();
  final RxBool isTorchOn = false.obs;
  final Rx<CameraFacing> cameraFacing = CameraFacing.back.obs;

  @override
  void initState() {
    super.initState();
    controller = MobileScannerController();
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  void toggleTorch() {
    isTorchOn.value = !isTorchOn.value;
    controller.toggleTorch();
  }

  void switchCamera() {
    cameraFacing.value = cameraFacing.value == CameraFacing.back
        ? CameraFacing.front
        : CameraFacing.back;
    controller.switchCamera();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Scan QR Code'),
        actions: [
          IconButton(
            icon: Obx(() => Icon(
                  isTorchOn.value ? Icons.flash_on : Icons.flash_off,
                )),
            onPressed: toggleTorch,
          ),
          IconButton(
            icon: Obx(() => Icon(
                  cameraFacing.value == CameraFacing.front
                      ? Icons.camera_front
                      : Icons.camera_rear,
                )),
            onPressed: switchCamera,
          ),
        ],
      ),
      body: Stack(
        children: [
          MobileScanner(
            controller: controller,
            onDetect: (capture) {
              qrScannerController.onDetect(controller,capture);
            },
          ),
          CustomPaint(
            size: Size.infinite,
            painter: ScannerOverlayPainter(),
          ),
        ],
      ),
    );
  }
}

class ScannerOverlayPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = Colors.black54
      ..style = PaintingStyle.fill;

    final scanAreaSize = size.width * 0.7;
    final scanAreaLeft = (size.width - scanAreaSize) / 2;
    final scanAreaTop = (size.height - scanAreaSize) / 2;

    // Draw semi-transparent overlay
    Path path = Path()
      ..addRect(Rect.fromLTWH(0, 0, size.width, size.height))
      ..addRect(Rect.fromLTWH(
        scanAreaLeft,
        scanAreaTop,
        scanAreaSize,
        scanAreaSize,
      ));

    canvas.drawPath(path, paint);

    // Draw scan area border
    final borderPaint = Paint()
      ..color = Colors.white
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2.0;

    canvas.drawRect(
      Rect.fromLTWH(
        scanAreaLeft,
        scanAreaTop,
        scanAreaSize,
        scanAreaSize,
      ),
      borderPaint,
    );

    // Draw corner markers
    final markerLength = scanAreaSize * 0.1;
    final markerPaint = Paint()
      ..color = Colors.blue
      ..style = PaintingStyle.stroke
      ..strokeWidth = 4.0;

    // Top left corner
    canvas.drawLine(
      Offset(scanAreaLeft, scanAreaTop),
      Offset(scanAreaLeft + markerLength, scanAreaTop),
      markerPaint,
    );
    canvas.drawLine(
      Offset(scanAreaLeft, scanAreaTop),
      Offset(scanAreaLeft, scanAreaTop + markerLength),
      markerPaint,
    );

    // Top right corner
    canvas.drawLine(
      Offset(scanAreaLeft + scanAreaSize, scanAreaTop),
      Offset(scanAreaLeft + scanAreaSize - markerLength, scanAreaTop),
      markerPaint,
    );
    canvas.drawLine(
      Offset(scanAreaLeft + scanAreaSize, scanAreaTop),
      Offset(scanAreaLeft + scanAreaSize, scanAreaTop + markerLength),
      markerPaint,
    );

    // Bottom left corner
    canvas.drawLine(
      Offset(scanAreaLeft, scanAreaTop + scanAreaSize),
      Offset(scanAreaLeft + markerLength, scanAreaTop + scanAreaSize),
      markerPaint,
    );
    canvas.drawLine(
      Offset(scanAreaLeft, scanAreaTop + scanAreaSize),
      Offset(scanAreaLeft, scanAreaTop + scanAreaSize - markerLength),
      markerPaint,
    );

    // Bottom right corner
    canvas.drawLine(
      Offset(scanAreaLeft + scanAreaSize, scanAreaTop + scanAreaSize),
      Offset(scanAreaLeft + scanAreaSize - markerLength,
          scanAreaTop + scanAreaSize),
      markerPaint,
    );
    canvas.drawLine(
      Offset(scanAreaLeft + scanAreaSize, scanAreaTop + scanAreaSize),
      Offset(scanAreaLeft + scanAreaSize,
          scanAreaTop + scanAreaSize - markerLength),
      markerPaint,
    );
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}
