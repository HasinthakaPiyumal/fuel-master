package com.uokse.fuelmaster.dto.Request;

import com.uokse.fuelmaster.model.FuelStation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;
    @NotBlank(message = "NIC is required")
    @Pattern(regexp = "^[0-9]{9}[vVxX]?$|^[0-9]{12}$", message = "Invalid NIC format")
    private String nic;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotNull(message = "FuelStation Id is mandatory")
    private Long fuelStation;

    public EmployeeDTO(String id, String name, String phone, String nic, String password, Long fuelStation) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.nic = nic;
        this.password = password;
        this.fuelStation= fuelStation;
    }

    public EmployeeDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Long getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(Long fuelStation) {
        this.fuelStation = fuelStation;
    }
}
