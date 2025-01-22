package com.uokse.fuelmaster.Service.impl;

import com.uokse.fuelmaster.DTO.LoginDTO;
import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Repo.UserRepo;
import com.uokse.fuelmaster.Response.LoginResponse;
import com.uokse.fuelmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserIMPL  {

    @Autowired
    private static UserRepo userRepo;


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


    public LoginResponse loginUser(LoginDTO loginDTO) {
      String msg="";
      User user = userRepo.findByPhone(loginDTO.getPhone());
      if(user!=null){
          String inputPassword = loginDTO.getPassword();
          String storedPassword= user.getPassword();
          if(inputPassword.equals(storedPassword)){
              Optional<User> validUser = userRepo.findOneByPhoneAndPassword(loginDTO.getPhone(),storedPassword);
              if(validUser.isPresent()){
                  return new LoginResponse("Login Success",true);
              }else{
                  return new LoginResponse("Login Failed",false);
              }
          } else{
              return new LoginResponse("Password Not Match",false);
          }

      }else{
          return new LoginResponse("Phone Number Not exits",false);
      }
    }



    public static List<UserDTO> getAllUsers() {
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





    public static UserDTO getUserById(Long id) {
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
