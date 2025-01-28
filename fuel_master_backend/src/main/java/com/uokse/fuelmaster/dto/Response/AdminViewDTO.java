package com.uokse.fuelmaster.dto.Response;

public class AdminViewDTO {
    private String name;
    private String phone;
    private String nic;

    public AdminViewDTO(String name, String phone, String nic) {
        this.name = name;
        this.phone = phone;
        this.nic = nic;
    }

    public AdminViewDTO() {
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
