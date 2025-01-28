package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.VehicleTypeDTO;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.repository.VehicleTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleType addVehicleType(VehicleTypeDTO vehicleTypeDTO) {
        // Check for duplicate vehicle type
        Optional<VehicleType> existingVehicleType = vehicleTypeRepository.findByVehicleTypeAndFuelType(
                vehicleTypeDTO.getVehicleType(), vehicleTypeDTO.getFuelType());

        if (existingVehicleType.isPresent()) {
            throw new IllegalArgumentException("Vehicle type with the same type and fuel type already exists.");
        }

        VehicleType vehicleType = new VehicleType();
        vehicleType.setVehicleType(vehicleTypeDTO.getVehicleType());
        vehicleType.setFuelType(vehicleTypeDTO.getFuelType());
        vehicleType.setDefaultQuota(vehicleTypeDTO.getDefaultQuota());
        return vehicleTypeRepository.save(vehicleType);
    }

    public List<VehicleType> getAllVehicleTypes() {
        return vehicleTypeRepository.findAll();
    }
}
