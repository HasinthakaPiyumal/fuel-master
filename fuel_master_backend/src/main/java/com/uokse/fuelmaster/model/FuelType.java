package com.uokse.fuelmaster.model;
public enum FuelType {
    PETROL,
    DIESEL;

    public static FuelType getFuelType(String fuelType){
        return FuelType.valueOf(fuelType);
    }

}