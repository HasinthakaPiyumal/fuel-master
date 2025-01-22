package com.uokse.fuelmaster.Service.impl;

import com.uokse.fuelmaster.DTO.LoginDTO;
import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Repo.UserRepo;
import com.uokse.fuelmaster.Response.LoginResponse;
import com.uokse.fuelmaster.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIMPL  {

    @Autowired
    private UserRepo userRepo;


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
}
