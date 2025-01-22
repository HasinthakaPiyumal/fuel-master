package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    String addUser(UserDTO userDTO);

    Optional<User> loginUser(LoginDTO loginDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);
}