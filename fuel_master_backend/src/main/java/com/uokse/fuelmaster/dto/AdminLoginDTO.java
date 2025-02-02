package com.uokse.fuelmaster.dto;

import jakarta.validation.constraints.NotBlank;

public class AdminLoginDTO {
    @NotBlank(message = "Email is required")
    @jakarta.validation.constraints.Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

    public AdminLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AdminLoginDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
