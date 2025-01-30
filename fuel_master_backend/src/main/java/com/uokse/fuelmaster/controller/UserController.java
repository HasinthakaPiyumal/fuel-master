package com.uokse.fuelmaster.controller;


import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.impl.UserIMPL;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.JwtService;
import jakarta.validation.Valid;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            // Extract validation error messages
            HashMap<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }
        try{
            Long id = userIMPL.addUser(userDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("UserId", id);
            SuccessResponse successResponse = new SuccessResponse(
                    "User saved successfully",
                    true,
                    data
            );
            return ResponseEntity.ok(successResponse);
        }catch (IllegalArgumentException e) {  // Catch the specific exception
            ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }

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
            ErrorResponse errorResponse = new ErrorResponse(401, "Login Failed");
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userIMPL.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Users Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userIMPL.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "User Not Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }
}
