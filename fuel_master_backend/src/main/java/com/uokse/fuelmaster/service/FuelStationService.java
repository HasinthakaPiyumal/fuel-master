package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        FuelStation fuelStation = new FuelStation();
        fuelStation.setRegNo(fuelStationDTO.getRegNo());
        fuelStation.setLocation(fuelStationDTO.getLocation());
        fuelStation.setOwner(fuelStationDTO.getOwner());
        fuelStation.setEmployeeCount(fuelStationDTO.getEmployeeCount());

        fuelStationRepo.save(fuelStation);

        return "Fuel station registered successfully.";
    }

    // New method to fetch all fuel stations
    public List<FuelStationDTO> getAllFuelStations() {
        List<FuelStation> stations = fuelStationRepo.findAll();
        return stations.stream()
                .map(station -> new FuelStationDTO(
                        station.getId(),
                        station.getRegNo(),
                        station.getLocation(),
                        station.getOwner(),
                        station.getEmployeeCount()))
                .toList();
    }

    // New method to fetch a single fuel station by ID
    public FuelStationDTO getFuelStationById(Long id) {
        FuelStation station = fuelStationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with ID: " + id));
        return new FuelStationDTO(
                station.getId(),
                station.getRegNo(),
                station.getLocation(),
                station.getOwner(),
                station.getEmployeeCount());
    }

    //update existing fuel station
    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
        Optional<FuelStation> existingFuelStationOpt = fuelStationRepo.findById(id);

        if (existingFuelStationOpt.isPresent()) {
            FuelStation existingFuelStation = existingFuelStationOpt.get();

            existingFuelStation.setRegNo(updatedFuelStation.getRegNo());
            existingFuelStation.setOwner(updatedFuelStation.getOwner());
            existingFuelStation.setLocation(updatedFuelStation.getLocation());
            existingFuelStation.setEmployeeCount(updatedFuelStation.getEmployeeCount());

            return fuelStationRepo.save(existingFuelStation);
        } else {
            throw new IllegalArgumentException("Fuel Station not found with id: " + id);
        }
    }

}