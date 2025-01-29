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
    private FuelStationRepo fuelStationRepository;

    public FuelStation addFuelStation(FuelStationDTO fuelStationDTO) {
        FuelStation fuelStation = new FuelStation();
        fuelStation.setRegNo(fuelStationDTO.getRegNo());
        fuelStation.setLocation(fuelStationDTO.getLocation());
        fuelStation.setOwner(fuelStationDTO.getOwner());
        fuelStation.setEmployeeCount(fuelStationDTO.getEmployeeCount());

        return fuelStationRepository.save(fuelStation);
    }

    public FuelStation updateFuelStation(Long id, FuelStationDTO fuelStationDTO) {
        Optional<FuelStation> fuelStationOptional = fuelStationRepository.findById(id);
        if (fuelStationOptional.isPresent()) {
            FuelStation fuelStation = fuelStationOptional.get();
            fuelStation.setRegNo(fuelStationDTO.getRegNo());
            fuelStation.setLocation(fuelStationDTO.getLocation());
            fuelStation.setOwner(fuelStationDTO.getOwner());
            fuelStation.setEmployeeCount(fuelStationDTO.getEmployeeCount());
            return fuelStationRepository.save(fuelStation);
        } else {
            throw new RuntimeException("Fuel station not found");
        }
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
