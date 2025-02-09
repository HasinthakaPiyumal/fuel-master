package com.uokse.fuelmaster.initializer;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.model.*;
import com.uokse.fuelmaster.repository.*;
import com.uokse.fuelmaster.service.AdminService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AdminInitializer {
@Bean
ApplicationRunner initAdmin(UserRepo userRepo,AdminRepository adminRepository, VehicleTypeRepository vehicleTypeRepository, AdminService adminService, FuelStationRepo fuelStationRepo, EmployeeRepository employeeRepository, VehicleRepo vehicleRepo,FuelTransactionRepository fuelTransactionRepository) {
    return args -> {
        if (adminRepository.findAll().isEmpty()) {
            AdminDTO admin = new AdminDTO();
            admin.setName("John Doe");
            admin.setEmail("admin@fuelmaster.com");
            admin.setRole(AdminType.SUPER_ADMIN);
            admin.setPassword("admin");
            admin.setNic("123456789V");
            adminService.addAdmin(admin);


            AdminDTO stationAdmin = new AdminDTO();
            stationAdmin.setName("Kasun Perera");
            stationAdmin.setEmail("kasun@station.com");
            stationAdmin.setRole(AdminType.STATION_MANAGER);
            stationAdmin.setPassword("admin");
            stationAdmin.setNic("123456788V");
            adminService.addAdmin(stationAdmin);

            AdminDTO stationAdmin2 = new AdminDTO();
            stationAdmin2.setName("Sandun Dinesh");
            stationAdmin2.setEmail("sandun@station.com");
            stationAdmin2.setRole(AdminType.STATION_MANAGER);
            stationAdmin2.setPassword("admin");
            stationAdmin2.setNic("123456888V");
            adminService.addAdmin(stationAdmin2);

            System.out.println("✅ Default ADMIN user created successfully.");
        }
        if (fuelStationRepo.findAll().isEmpty()){
            FuelStation fuelStation = new FuelStation();
            fuelStation.setLocation("Colombo");
            fuelStation.setOwnerName("John Doe");
            fuelStation.setRegNo("AY8IK9MK");
            fuelStation.setOwnerId(2L);
            fuelStationRepo.save(fuelStation);

            FuelStation fuelStation2 = new FuelStation();
            fuelStation2.setLocation("Kelaniya");
            fuelStation2.setRegNo("AO8IK9MK");
            fuelStation2.setOwnerName("John Doe");
            fuelStation2.setOwnerId(3L);
            fuelStationRepo.save(fuelStation2);

            System.out.println("✅ Default Fuel Station created successfully.");
        }
        if (vehicleTypeRepository.findAll().isEmpty()) {

            VehicleType vehicleType = new VehicleType();
            vehicleType.setVehicleType("Car");
            vehicleType.setFuelType(FuelType.PETROL);
            vehicleType.setDefaultQuota(10L);
            vehicleTypeRepository.save(vehicleType);

            VehicleType vehicleType2 = new VehicleType();
            vehicleType2.setVehicleType("Car");
            vehicleType2.setFuelType(FuelType.DIESEL);
            vehicleType2.setDefaultQuota(12L);
            vehicleTypeRepository.save(vehicleType2);

            VehicleType vehicleType3 = new VehicleType();
            vehicleType3.setVehicleType("Bike");
            vehicleType3.setFuelType(FuelType.PETROL);
            vehicleType3.setDefaultQuota(9L);
            vehicleTypeRepository.save(vehicleType3);

            VehicleType vehicleType4 = new VehicleType();
            vehicleType4.setVehicleType("Van");
            vehicleType4.setFuelType(FuelType.PETROL);
            vehicleType4.setDefaultQuota(9L);
            vehicleTypeRepository.save(vehicleType4);

            System.out.println("✅ Default ADMIN user created successfully.");
        }

        if (employeeRepository.findAll().isEmpty()){
            Employee employee = new Employee();
            employee.setName("John Doe");
            employee.setPhone("0712345678");
            employee.setNic("123456789V");
            employee.setFuelStation(fuelStationRepo.getReferenceById(1L));
            employee.setPassword("12341234");
            employeeRepository.save(employee);

            Employee employee2 = new Employee();
            employee2.setName("Jane Doe");
            employee2.setPhone("0722345678");
            employee2.setNic("223456789V");
            employee2.setFuelStation(fuelStationRepo.getReferenceById(2L));
            employee2.setPassword("12341234");
            employeeRepository.save(employee2);

            Employee employee3 = new Employee();
            employee3.setName("Kane Doe");
            employee3.setPhone("0732345678");
            employee3.setNic("323456789V");
            employee3.setFuelStation(fuelStationRepo.getReferenceById(2L));
            employee3.setPassword("12341234");
            employeeRepository.save(employee3);

            System.out.println("✅ Default Employee created successfully.");
        }

        if(userRepo.findAll().isEmpty()){
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setNic("123456789v");
            user.setPhone("0712345678");
            user.setPassword("12341234");
            user.setVerified(true);
            userRepo.save(user);
        }

        if(vehicleRepo.findAll().isEmpty()){
            Vehicle vehicle = new Vehicle();
            vehicle.setChassisNumber("WP-AB-1234");
            vehicle.setVehicleType(vehicleTypeRepository.getReferenceById(1L));
            vehicle.setVehicleRegistrationPart1("ABC");
            vehicle.setVehicleRegistrationPart2(1234L);
            vehicle.setUser(userRepo.getReferenceById(1L));
            vehicleRepo.save(vehicle);

            System.out.println("✅ Default Vehicle created successfully.");
        }
        if(fuelTransactionRepository.findAll().isEmpty()){
            FuelTransaction fuelTransaction = new FuelTransaction();
            fuelTransaction.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction.setPumpedQuantity(4d);
            fuelTransaction.setTransactionDate(LocalDateTime.parse("2025-02-03T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction);

            FuelTransaction fuelTransaction2 = new FuelTransaction();
            fuelTransaction2.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction2.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction2.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction2.setPumpedQuantity(9d);
            fuelTransaction2.setTransactionDate(LocalDateTime.parse("2025-02-04T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction2);

            FuelTransaction fuelTransaction3 = new FuelTransaction();
            fuelTransaction3.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction3.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction3.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction3.setPumpedQuantity(2d);
            fuelTransaction3.setTransactionDate(LocalDateTime.parse("2025-02-05T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction3);

            FuelTransaction fuelTransaction4 = new FuelTransaction();
            fuelTransaction4.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction4.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction4.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction4.setPumpedQuantity(7d);
            fuelTransaction4.setTransactionDate(LocalDateTime.parse("2025-02-06T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction4);

            FuelTransaction fuelTransaction5 = new FuelTransaction();
            fuelTransaction5.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction5.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction5.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction5.setPumpedQuantity(4d);
            fuelTransaction5.setTransactionDate(LocalDateTime.parse("2025-02-07T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction5);

            FuelTransaction fuelTransaction6 = new FuelTransaction();
            fuelTransaction6.setFuelStation(fuelStationRepo.getReferenceById(1L));
            fuelTransaction6.setVehicle(vehicleRepo.getReferenceById(1L));
            fuelTransaction6.setEmployee(employeeRepository.getReferenceById(1L));
            fuelTransaction6.setPumpedQuantity(8d);
            fuelTransaction6.setTransactionDate(LocalDateTime.parse("2025-02-08T10:15:30"));
            fuelTransactionRepository.save(fuelTransaction6);

            System.out.println("✅ Default Fuel Transaction created successfully.");
        }
    };
}}