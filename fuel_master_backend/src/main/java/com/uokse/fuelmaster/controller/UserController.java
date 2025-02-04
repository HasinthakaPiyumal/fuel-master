package com.uokse.fuelmaster.controller;


import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.Response.PhoneNumberDTO;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.impl.UserIMPL;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.JwtService;
import jakarta.validation.Valid;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
            ErrorResponse errorResponse = new ErrorResponse(400, errors.get(errors.keySet().toArray()[0]));

            return ResponseEntity.badRequest().body(errorResponse);
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
        System.out.println("login" + loginDTO);
        Optional<User> loggedUser = userIMPL.loginUser(loginDTO);
        if(loggedUser != null){
            String token = jwtService.generateToken(loggedUser.get());
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", loggedUser.get());
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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User userDTO = userIMPL.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "User Not Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @PostMapping("/change-phone")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> changePhone(@RequestBody PhoneNumberDTO phoneNumberDTO) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userIMPL.updateUserPhoneNumber(Long.parseLong(userId),phoneNumberDTO.getPhoneNumber());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "User Not Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/authenticate")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> authenticateUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userIMPL.getUserById(Long.parseLong(userId));
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "User Not Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    //remove user
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> removeUser(@PathVariable Long id){
        try{
        userIMPL.removeUser(id);
         return ResponseEntity.ok(new SuccessResponse("User deleted successfully",true,null));
    }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
        }
}
