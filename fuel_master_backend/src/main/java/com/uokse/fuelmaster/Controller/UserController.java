package com.uokse.fuelmaster.Controller;


import com.uokse.fuelmaster.DTO.LoginDTO;
import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Response.LoginResponse;
import com.uokse.fuelmaster.Service.impl.UserIMPL;
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
    private UserIMPL userIMPL;

    @PostMapping(path="/save")
    public String saveUser(@RequestBody UserDTO userDTO ){
        String id = userIMPL.addUser(userDTO);
        return ("User saved with ID: " + id);
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        LoginResponse loginResponse = userIMPL.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}
