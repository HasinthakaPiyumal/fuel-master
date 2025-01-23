package com.uokse.fuelmaster.Controller;


import com.uokse.fuelmaster.DTO.LoginDTO;
import com.uokse.fuelmaster.Service.impl.UserIMPL;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.response.LoginResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.JwtService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uokse.fuelmaster.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserIMPL userIMPL;

    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(path="/save")
    public String saveUser(@RequestBody UserDTO userDTO ){
        String id = userIMPL.addUser(userDTO);
        return ("User saved with ID: " + id);
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        Optional<User> loggedUser = userIMPL.loginUser(loginDTO);
        if(loggedUser != null){
            String token = jwtService.generateToken(loggedUser.get());
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", loggedUser.get().getUserMap());
            data.put("token", token);
            SuccessResponse successResponse = new SuccessResponse(
                "Login Success",
                true,
                data
            );
            return ResponseEntity.ok(successResponse);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userIMPL.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(404).body("No users found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userIMPL.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
