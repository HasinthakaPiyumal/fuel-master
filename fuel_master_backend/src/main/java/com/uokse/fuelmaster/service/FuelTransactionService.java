package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.FuelTransaction;
import com.uokse.fuelmaster.model.Vehicle;
import com.uokse.fuelmaster.model.fuelStation;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import com.uokse.fuelmaster.repository.FuelTransactionRepository;
import com.uokse.fuelmaster.repository.VehicleRepo;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

public class FuelTransactionService {

    private FuelTransactionRepository fuelTransactionRepository;
    private VehicleRepo vehicleRepo;
    private EmployeeRepository employeeRepository;
    private FuelStationRepo fuelStationRepo;
    private FuelTransaction fuelTransaction;

    public FuelTransactionService(FuelTransactionRepository fuelTransactionRepository, VehicleRepo vehicleRepo, EmployeeRepository employeeRepository, FuelStationRepo fuelStationRepo) {
        this.fuelTransactionRepository = fuelTransactionRepository;
        this.vehicleRepo= vehicleRepo;
        this.employeeRepository = employeeRepository;
        this.fuelStationRepo = fuelStationRepo;
    }

    @Transactional
    public String addFuelTransaction(Long vehicleId, Long employeeId, Long fuelStationId, Double pumpedQuantity) {
        // Fetch the required entities
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        fuelStation station = fuelStationRepo.findById(fuelStationId).orElseThrow(() -> new RuntimeException("Fuel station not found"));


        Double availableQuantity = vehicle.getAvailableFuel();

        // Validate if the pumped quantity can be processed
        if (pumpedQuantity > availableQuantity) {
            throw new RuntimeException("Pumped quantity exceeds available fuel capacity for this week");
        }

        // Deduct the pumped quantity from the available quantity
        Double updatedAvailableQuantity = availableQuantity - pumpedQuantity;

        // Create and save the transaction
        FuelTransaction transaction = new FuelTransaction();
        transaction.setVehicle(vehicle);
        transaction.setEmployee(employee);
        transaction.setFuelStation(station);
        transaction.setPumpedQuantity(pumpedQuantity);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAvailableQuantity(updatedAvailableQuantity);

        fuelTransactionRepository.save(transaction);

        vehicle.setAvailableFuel(updatedAvailableQuantity);
        vehicleRepo.save(vehicle);


        return "Fuel transaction added successfully for vehicle ID: " + vehicleId;
    }
}
