package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.VehicleTypeDTO;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.repository.VehicleTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public VehicleType updateVehicleType(Long id, VehicleType updatedVehicleType) {
        Optional<VehicleType> existingVehicleType = vehicleTypeRepository.findById(id);
         if (existingVehicleType.isPresent()) {
             VehicleType vehicleType = existingVehicleType.get();
              vehicleType.setVehicleType(updatedVehicleType.getVehicleType());
              vehicleType.setFuelType(updatedVehicleType.getFuelType());
              vehicleType.setDefaultQuota(updatedVehicleType.getDefaultQuota());
              return vehicleTypeRepository.save(vehicleType);
         }else{
             throw new IllegalArgumentException("Vehicle type with ID " + id + " not found.");
         }

    }

    //Get vehicle type by id
    public VehicleType getVehicleTypeById(Long id) {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle type not found"));
    }

    public void deleteVehicleType(Long id) {
        if (vehicleTypeRepository.existsById(id)) {
            vehicleTypeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Vehicle type not found");
        }
    }

    public VehicleType updateDefaultQuota(Long id, Long newQuota) {
        // Find the vehicle type by ID
        VehicleType vehicleType = vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vehicle type not found"));

        // Update the default quota
        vehicleType.setDefaultQuota(newQuota);

        // Save and return the updated entity
        return vehicleTypeRepository.save(vehicleType);
    }

}
