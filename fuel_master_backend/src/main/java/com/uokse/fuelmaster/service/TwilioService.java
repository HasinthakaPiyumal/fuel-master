package com.uokse.fuelmaster.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uokse.fuelmaster.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
@Service
public class TwilioService {
    private final TwilioConfig twilioConfig;

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public boolean sendSmsNotification(String phoneNumber, String message) {
        try {
            Message smsMessage = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                twilioConfig.getVerifyServiceSid(),
                message
            ).create();
            return !smsMessage.getStatus().equals(Message.Status.FAILED);
        } catch (Exception e) {
            return false;
        }
    }
}