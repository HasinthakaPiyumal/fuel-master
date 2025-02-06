package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.repository.AdminRepository;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelStationService {

    @Autowired
    private FuelStationRepo fuelStationRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public FuelStation addFuelStation(FuelStationDTO fuelStationDTO) {
        FuelStation fuelStation = new FuelStation();
        fuelStation.setRegNo(fuelStationDTO.getRegNo());
        fuelStation.setLocation(fuelStationDTO.getLocation());
        fuelStation.setOwnerId(fuelStationDTO.getOwnerId());

        Optional<Admin> owner = adminRepository.findById(fuelStationDTO.getOwnerId());
//        Optional<Admin> owner = fuelStation.(fuelStationDTO.getOwnerId());
        if (owner.isPresent()) {
            fuelStation.setOwnerName(owner.get().getName());
        } else {
            throw new RuntimeException("Owner not found with ID: " + fuelStationDTO.getOwnerId());
        }


        return fuelStationRepository.save(fuelStation);
    }

    public FuelStation updateFuelStation(Long id, FuelStationDTO fuelStationDTO) {
        Optional<FuelStation> fuelStationOptional = fuelStationRepository.findById(id);
        if (fuelStationOptional.isPresent()) {
            FuelStation fuelStation = fuelStationOptional.get();
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

    @Transactional
    public void deleteFuelStation(Long id) {
        if (fuelStationRepository.findById(id).isPresent()) {
            employeeRepository.deleteByFuelStationId(id);
            fuelStationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Fuel Station is not found");
        }
    }
}
