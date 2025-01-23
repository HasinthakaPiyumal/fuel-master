package com.uokse.fuelmaster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.verify-service-sid}")
    private String verifyServiceSid;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public String getVerifyServiceSid() {
        return verifyServiceSid;
    }
} 