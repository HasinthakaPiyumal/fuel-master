package com.uokse.fuelmaster.dto;

public class MobileVerificationDTO {
    private String code;

    public MobileVerificationDTO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
