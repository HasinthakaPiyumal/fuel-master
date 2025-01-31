package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.model.*;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import com.uokse.fuelmaster.repository.FuelTransactionRepository;
import com.uokse.fuelmaster.repository.VehicleRepo;
import com.uokse.fuelmaster.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

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

    public Double getFuelQuantity(Long vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        LocalDateTime startOfWeek = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<FuelTransaction> fuelTransaction = fuelTransactionRepository.findTransactionsForCurrentWeek(vehicle, startOfWeek);
        double totalUseFuelQuota = 0.0;
        for (int i = 0; i < fuelTransaction.size() ; i++) {
            totalUseFuelQuota += fuelTransaction.get(i).getPumpedQuantity();
        }
        return totalUseFuelQuota;
    }

    public Optional<FuelTransaction> getLastTransaction(Long vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return fuelTransactionRepository.findFirstByVehicleOrderByTransactionDate(vehicle);
    }

    public List<FuelTransaction> getEmployeeTransactions() {
        Long employeeId = Long.parseLong(AuthUtil.getCurrentUserId());
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        return fuelTransactionRepository.findByEmployeeAndToday(employee);
    }

    @Transactional
    public Object addFuelTransaction(Long vehicleId,  Double pumpedQuantity) {
        Long employeeId = Long.parseLong(AuthUtil.getCurrentUserId());
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        FuelStation fuelStation = fuelStationRepo.findById(employee.getFuelStation().getId()).orElseThrow(() -> new RuntimeException("Fuel station not found"));
        double availableFuelQuota = vehicle.getVehicleType().getDefaultQuota() - getFuelQuantity(vehicleId);
        if (pumpedQuantity > availableFuelQuota) {
            throw new RuntimeException("Fuel quota exceeded");
        }
        return fuelTransactionRepository.save(new FuelTransaction(null, vehicle, employee, fuelStation, pumpedQuantity, LocalDateTime.now()));
    }
}
