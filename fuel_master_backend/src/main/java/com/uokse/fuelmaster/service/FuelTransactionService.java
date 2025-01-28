package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.model.*;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import com.uokse.fuelmaster.repository.FuelTransactionRepository;
import com.uokse.fuelmaster.repository.VehicleRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FuelTransactionService {

    private FuelTransactionRepository fuelTransactionRepository;
    private EmployeeRepository employeeRepository;
    private FuelStationRepo fuelStationRepo;
    private VehicleRepo vehicleRepo;


    public FuelTransactionService(FuelTransactionRepository fuelTransactionRepository, EmployeeRepository employeeRepository, FuelStationRepo fuelStationRepo, VehicleRepo vehicleRepo) {
        this.fuelTransactionRepository = fuelTransactionRepository;
        this.employeeRepository = employeeRepository;
        this.fuelStationRepo = fuelStationRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    public String addFuelTransaction(Long vehicleId, Long employeeId, Long fuelStationId, Double pumpedQuantity) {

        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        FuelStation fuelStation = fuelStationRepo.findById(fuelStationId).orElseThrow(() -> new RuntimeException("Fuel station not found"));

        // Get the last transaction for the vehicle
        FuelTransaction lastTransaction = (FuelTransaction) fuelTransactionRepository.findFirstByVehicleOrderByTransactionDateDesc(vehicle).orElse(null);

        // Use the availableQuantity from the last transaction, or initialize a default value
        Double availableQuota = lastTransaction != null ? lastTransaction.getAvailableQuota() : 100.0; // Default to 100.0 if no previous transaction exists

        // Calculate the updated available quota
        Double updatedAvailableQuota = availableQuota - pumpedQuantity;
        // Validate if the pumped quantity can be processed
        if (pumpedQuantity > availableQuota) {
            throw new RuntimeException("Pumped quantity exceeds available fuel capacity for this week");
        }

        // Create and save the new fuel transaction
        FuelTransaction newTransaction = new FuelTransaction();
        newTransaction.setVehicle(vehicle);
        newTransaction.setEmployee(employee);
        newTransaction.setFuelStation(fuelStation);
        newTransaction.setPumpedQuantity(pumpedQuantity);
        newTransaction.setTransactionDate(LocalDateTime.now());
        newTransaction.setAvailableQuota(updatedAvailableQuota);

        fuelTransactionRepository.save(newTransaction);

        return "Fuel transaction added successfully for vehicle ID: " + vehicleId;
    }
}
