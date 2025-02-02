package com.uokse.fuelmaster.service.notification;

import com.uokse.fuelmaster.service.TwilioService;
import org.springframework.stereotype.Service;

import static com.uokse.fuelmaster.util.PhoneNumberUtil.formatPhoneNumber;

@Service
public class TwilioNotification implements NotificationStrategy {
    private final TwilioService twilioService;

    public TwilioNotification(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Override
    public boolean sendNotification(String phoneNumber, String message) {
        return twilioService.sendSmsNotification(formatPhoneNumber(phoneNumber), message);
    }
}
