package com.uokse.fuelmaster.dto.Request;

import com.uokse.fuelmaster.model.FuelStation;

public class EmployeeDTO {
    private String id;
    private String name;
    private String phone;
    private String nic;
    private String password;
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
