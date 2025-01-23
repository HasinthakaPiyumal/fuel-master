package com.uokse.fuelmaster.DTO;

public class LoginDTO {
    private String phone;
    private String password;

    public LoginDTO(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public LoginDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
