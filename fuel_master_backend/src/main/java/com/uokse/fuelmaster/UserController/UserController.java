package com.uokse.fuelmaster.UserController;


import com.uokse.fuelmaster.DTO.LoginDTO;
import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Response.LoginResponse;
import com.uokse.fuelmaster.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path="/save")
    public String saveUser(@RequestBody UserDTO userDTO ){
        String id = userService.addUser(userDTO);
        return ("User saved with ID: " + id);
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}
