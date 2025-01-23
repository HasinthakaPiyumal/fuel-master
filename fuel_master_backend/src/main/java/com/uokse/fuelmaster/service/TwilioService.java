package com.uokse.fuelmaster.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uokse.fuelmaster.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
@Service
public class TwilioService {

    @Autowired
    private TwilioConfig twilioConfig;

    public String sendSmsNotification(String phoneNumber, String message) {
        System.out.println("Sending SMS notification to: " + phoneNumber);
        try {
            Message smsMessage = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                twilioConfig.getVerifyServiceSid(),
                message
            ).create();
            System.out.println(smsMessage.getSid());
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Error sending SMS notification: " + e.getMessage());
        }
    }
} 