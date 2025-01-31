import 'package:intl/intl.dart';
import 'package:timezone/timezone.dart' as tz;
import 'package:timezone/data/latest.dart' as tz;

    class DateTimeUtils {
  static void initTimeZone() {
    tz.initializeTimeZones();
  }

  static String formatTimestamp(DateTime timestamp) {
    final sriLanka = tz.getLocation('Asia/Colombo');
    final localTime = tz.TZDateTime.from(timestamp.toUtc(), sriLanka);
    
    return DateFormat('HH:mm').format(localTime);
  }

  static String formatTimestamp12Hour(DateTime timestamp) {
    final sriLanka = tz.getLocation('Asia/Colombo');
    final localTime = tz.TZDateTime.from(timestamp.toUtc(), sriLanka);

    return DateFormat('hh:mm a').format(localTime);
  }
}
