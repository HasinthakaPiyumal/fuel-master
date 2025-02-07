package com.uokse.fuelmaster.service.impl;

import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.Vehicle;
import com.uokse.fuelmaster.repository.UserRepo;
import com.uokse.fuelmaster.repository.VehicleRepo;
import com.uokse.fuelmaster.response.LoginResponse;

import com.uokse.fuelmaster.service.JwtService;
import com.uokse.fuelmaster.service.VerificationCodeService;
import com.uokse.fuelmaster.util.PasswordUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserIMPL {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
     private VehicleIMPL vehicleIMPL;


    private VerificationCodeService verificationCodeService;
    private JwtService jwtService;

    public UserIMPL(VerificationCodeService verificationCodeService, JwtService jwtService) {
        this.verificationCodeService = verificationCodeService;
        this.jwtService = jwtService;
    }


    public String addUser(UserDTO userDTO) {

        String hashedPassword = PasswordUtil.hashPassword(userDTO.getPassword());
        if (userRepo.findByPhone(userDTO.getPhone()).isPresent()){
            throw new IllegalArgumentException("Phone number already registered: " +userDTO.getPhone());
        }
        else if(userRepo.findByNic(userDTO.getNic()).isPresent()){
            throw new IllegalArgumentException("NIC already registered: " +userDTO.getNic());
        }
            User user = new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhone(),
                userDTO.getNic(),
                hashedPassword);
        user.setVerified(false);
        userRepo.save(user);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        verificationCodeService.sendVerificationCode();
        return jwtService.generateToken(user);

    }

    public Optional<User> loginUser(LoginDTO loginDTO) {
        Optional<User> user = userRepo.findByPhone(loginDTO.getPhone());
        if (user.isPresent()) {
            String inputPassword = loginDTO.getPassword();
            String storedPassword = user.get().getPassword();
            if (PasswordUtil.verifyPassword(inputPassword, storedPassword)) {
                return user;
            }

        }
        return null;

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getNic(),
                null // Exclude password in the response
        )).toList();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            return null;
        }
    }

    public User updateUserPhoneNumber(Long id, String phoneNumber) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getVerified()){
                throw new IllegalArgumentException("User is verified. Phone number cannot be changed.");
            }
            Optional<User> userSearch = userRepo.findByPhone(phoneNumber);
            if (userSearch.isPresent()) {
                throw new IllegalArgumentException("Phone number already registered: " + phoneNumber);
            }
            user.setPhone(phoneNumber);
            userRepo.save(user);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            verificationCodeService.sendVerificationCode();
            return user;
        } else {
            return null;
        }
    }

    public User findUser(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        return userOptional.orElse(null);
    }

    public User getUserByPhone(String phone) {
        Optional<User> userOptional = userRepo.findByPhone(phone);
        return userOptional.orElse(null);
    }

    //remove user
    @Transactional
    public void removeUser(Long id) {
        if (userRepo.existsById(id)) {
            // First, get the associated vehicle
            Long vehicleId = vehicleRepo.findByUserId(id).getId();

            if (vehicleId != null) {
                vehicleIMPL.removeVehicle(vehicleId);  // Remove the associated vehicle
            } else {
                throw new RuntimeException("Vehicle not found for user");
            }

            userRepo.deleteById(id); // Now delete the user
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User updateUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepo.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPhone(userDTO.getPhone());
            user.setNic(userDTO.getNic());
            userRepo.save(user);
            return user;
        } else {
            return null;
        }
    }

}
