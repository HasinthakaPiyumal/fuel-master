package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.model.FuelQuota;
import com.uokse.fuelmaster.repository.FuelQuotaRepository;
import com.uokse.fuelmaster.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuotaService {

    @Autowired
    private FuelQuotaRepository fuelQuotaRepository;

    @Autowired
    private VehicleRepo vehicleRepository;

    @Scheduled(cron = "0 0 0 * * MON") // Runs every Monday at midnight
    public void resetWeeklyQuotas() {
        List<FuelQuota> fuelQuotas = fuelQuotaRepository.findAll();

        for (FuelQuota quota : fuelQuotas) {
            Long defaultQuota = quota.getVehicle().getVehicleType().getDefaultQuota();
            quota.setDefaultQuota(defaultQuota); // Reset to default quota
            quota.setUsedQuota(0L); // Reset used quota to 0
            quota.setQuotaDate(LocalDate.now()); // Update quota date
        }

        fuelQuotaRepository.saveAll(fuelQuotas);
        System.out.println("Weekly quotas have been reset successfully.");
    }
}
