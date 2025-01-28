package com.uokse.fuelmaster.dto;

public class EmployeeViewDetailsDTO {

    private String name;
    private String phone;
    private String nic;

    public EmployeeViewDetailsDTO(String name, String phone, String nic) {
        this.name = name;
        this.phone = phone;
        this.nic = nic;
    }

    public EmployeeViewDetailsDTO() {

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
}
