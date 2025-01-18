package com.uokse.fuelmaster.Service.impl;

import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Repo.UserRepo;
import com.uokse.fuelmaster.Service.UserService;
import com.uokse.fuelmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhone(),
                userDTO.getNic(),
                userDTO.getPassword()
        );
        userRepo.save(user);

        return user.getFirstName();
    }
}
