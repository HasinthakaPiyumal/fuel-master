package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.DTO.FuelStationDTO;
import com.uokse.fuelmaster.model.fuelStation;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelStationService {

    @Autowired
    private FuelStationRepo fuelStationRepo;

    public String registerFuelStation(FuelStationDTO fuelStationDTO) {
        // Check if the registration number is already used
        if (fuelStationRepo.existsByRegNo(fuelStationDTO.getRegNo())) {
            return "Error: Registration number already exists.";
        }

        // Save the fuel station
        fuelStation fuelStation = new fuelStation();
        fuelStation.setRegNo(fuelStationDTO.getRegNo());
        fuelStation.setLocation(fuelStationDTO.getLocation());
        fuelStation.setOwner(fuelStationDTO.getOwner());
        fuelStation.setEmployeeCount(fuelStationDTO.getEmployeeCount());

        fuelStationRepo.save(fuelStation);

        return "Fuel station registered successfully.";
    }
}