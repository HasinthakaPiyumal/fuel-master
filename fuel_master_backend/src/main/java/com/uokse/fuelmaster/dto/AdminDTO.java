package com.uokse.fuelmaster.dto;

import com.uokse.fuelmaster.model.AdminType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AdminDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is required")
    @jakarta.validation.constraints.Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Admin role is required")
    private AdminType role;

    @NotBlank(message = "NIC is required")
    @Pattern(regexp = "^[0-9]{9}[vVxX]?$|^[0-9]{12}$", message = "Invalid NIC format")
    private String nic;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private String createdAt;
    private String updatedAt;

    // Constructors
    public AdminDTO() {
    }

    public AdminDTO(Long id, String name, String email, AdminType role, String nic, String password, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nic = nic;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminType getRole() {
        return role;
    }

    public void setRole(AdminType role) {
        this.role = role;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
