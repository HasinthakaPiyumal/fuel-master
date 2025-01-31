package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.FuelTransaction;
import com.uokse.fuelmaster.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {

    @Query("SELECT ft FROM FuelTransaction ft WHERE ft.vehicle = :vehicle AND ft.transactionDate >= :startOfWeek ORDER BY ft.transactionDate DESC")
    List<FuelTransaction> findTransactionsForCurrentWeek(Vehicle vehicle, LocalDateTime startOfWeek);

    Optional<FuelTransaction> findFirstByVehicleOrderByTransactionDate(Vehicle vehicle);

    @Query("SELECT ft FROM FuelTransaction ft WHERE ft.employee = :employee AND DATE(ft.transactionDate) = CURRENT_DATE")
    List<FuelTransaction> findByEmployeeAndToday(@Param("employee") Employee employee);
}
