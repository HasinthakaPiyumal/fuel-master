package com.uokse.fuelmaster.service.impl;

import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.repository.UserRepo;
import com.uokse.fuelmaster.response.LoginResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserIMPL {

    @Autowired
    private UserRepo userRepo;


    public Long addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhone(),
                userDTO.getNic(),
                userDTO.getPassword());

        try {
            userRepo.save(user);

            return user.getId();
        } catch (Exception e) {
            System.out.println("User registration failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public Optional<User> loginUser(LoginDTO loginDTO) {
        Optional<User> user = userRepo.findByPhone(loginDTO.getPhone());
        if (user.isPresent()) {
            String inputPassword = loginDTO.getPassword();
            String storedPassword = user.get().getPassword();
            if (inputPassword.equals(storedPassword)) {
                Optional<User> validUser = userRepo.findOneByPhoneAndPassword(loginDTO.getPhone(), storedPassword);
                if (validUser.isPresent()) {
                    return user;
                }
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


    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhone(),
                    user.getNic(),
                    null // Exclude password in the response
            );
        } else {
            return null;
        }
    }

}
