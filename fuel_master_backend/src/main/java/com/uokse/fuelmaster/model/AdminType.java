package com.uokse.fuelmaster.model;
public enum AdminType {
    SUPER_ADMIN,
    STATION_MANAGER;

    public static AdminType getAdminType(String adminType){
        return AdminType.valueOf(adminType);
    }
}