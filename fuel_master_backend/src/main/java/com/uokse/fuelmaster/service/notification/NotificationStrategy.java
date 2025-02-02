package com.uokse.fuelmaster.service.notification;

public interface NotificationStrategy {
    boolean sendNotification(String recipient, String message);
}
