package com.uokse.fuelmaster.dto.Response;

public class AdminViewDTO {
    private String name;
    private String email;
    private String nic;

    public AdminViewDTO(String name, String email, String nic) {
        this.name = name;
        this.email = email;
        this.nic = nic;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
