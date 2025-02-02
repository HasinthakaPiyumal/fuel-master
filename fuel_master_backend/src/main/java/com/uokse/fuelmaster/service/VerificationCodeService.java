package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.VerificationCode;
import com.uokse.fuelmaster.repository.UserRepo;
import com.uokse.fuelmaster.repository.VerificationCodeRepository;
import com.uokse.fuelmaster.service.impl.UserIMPL;
import com.uokse.fuelmaster.service.notification.NotificationStrategy;
import com.uokse.fuelmaster.util.CodeGeneratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VerificationCodeService {
    @Autowired
    NotificationStrategy notificationStrategy;

    @Autowired
    VerificationCodeRepository verificationCodeRepository;

    @Autowired
    UserRepo userRepo;


    public String generateVerificationCode() {
        User obj = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj == null) {
            return null;
        }
        String code =  CodeGeneratorFactory.generateCode(6);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setUser(obj);
        verificationCode.setCreatedDate(new Date().getTime());
        verificationCodeRepository.save(verificationCode);
        return code;
    }

    public boolean verifyCode(String code) throws Exception {
        User obj = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj == null) {
            throw new Exception("User not found");
        }
        if(obj.getVerified()) {
            throw new Exception("User already verified");
        }
        VerificationCode verificationCode = verificationCodeRepository.findByCodeAndUser(code, obj);
        if(verificationCode == null) {
            throw new Exception("Invalid verification code");
        }
        long currentTime = new Date().getTime();
        long createdTime = verificationCode.getCreatedDate();
        long diff = currentTime - createdTime;
        if(diff > 300000) {
            throw new Exception("Verification code expired");
        }
        obj.setVerified(true);
        userRepo.save(obj);
        verificationCodeRepository.delete(verificationCode);
        return true;
    }

    public boolean sendVerificationCode() {
        User obj = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj == null) {
            return false;
        }
        String phoneNumber = obj.getPhone();
        try {
            String code = generateVerificationCode();
            System.out.println("Verification code: "+code);
            return true;
//            return  notificationStrategy.sendNotification(phoneNumber, "Your verification code is: "+code);
        } catch (Exception e) {
            System.out.println("Error sending verification code: " + e.getMessage());
            return false;
        }
    }
}
