package com.uokse.fuelmaster.dto.Response;

public class PhoneNumberDTO {
    private String phoneNumber;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
