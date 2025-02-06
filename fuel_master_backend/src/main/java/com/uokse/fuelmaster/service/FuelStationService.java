package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.repository.AdminRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FuelStationService {

    @Autowired
    private FuelStationRepo fuelStationRepository;

    @Autowired
    private AdminRepository adminRepository;

    public FuelStation addFuelStation(FuelStationDTO fuelStationDTO) {
        FuelStation fuelStation = new FuelStation();
        fuelStation.setRegNo(fuelStationDTO.getRegNo());
        fuelStation.setLocation(fuelStationDTO.getLocation());
        fuelStation.setOwnerId(fuelStationDTO.getOwnerId());

        Optional<Admin> owner = adminRepository.findById(fuelStationDTO.getOwnerId());
        if (owner.isPresent()) {
            fuelStation.setOwnerName(owner.get().getName());
        } else {
            throw new RuntimeException("Owner not found with ID: " + fuelStationDTO.getOwnerId());
        }


        return fuelStationRepository.save(fuelStation);
    }

    public FuelStation updateFuelStationOwner(Long id, Long ownerId) {
        // Find the fuel station by ID
        FuelStation fuelStation = fuelStationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fuel station not found"));

        // Find the new owner by ownerId
        Admin owner = adminRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("Owner not found with ID: " + ownerId));

        // Update only the owner fields
        fuelStation.setOwnerId(owner.getId());
        fuelStation.setOwnerName(owner.getName());

        // Save and return the updated entity
        return fuelStationRepository.save(fuelStation);
    }

    public List<FuelStation> getAllFuelStations() {
        return fuelStationRepository.findAll();
    }

    public FuelStation getFuelStationById(Long id) {
        Optional<FuelStation> fuelStationOptional = fuelStationRepository.findById(id);
        return fuelStationOptional.orElseThrow(() -> new RuntimeException("Fuel station not found"));
    }

    public void deleteFuelStation(Long id) {
        fuelStationRepository.deleteById(id);
    }
}
