package com.uokse.fuelmaster.Repo;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class MotorTrafficMockRepo {

    private static final Set<String> registeredChassisNumbers = new HashSet<>();

    static {
        // Mock data for registered chassis numbers
        registeredChassisNumbers.add("ABC12345");
        registeredChassisNumbers.add("DEF67890");
        registeredChassisNumbers.add("GHI54321");
        registeredChassisNumbers.add("JKL98765");
        registeredChassisNumbers.add("MNO43210");
        registeredChassisNumbers.add("PQR87654");
        registeredChassisNumbers.add("STU13579");
        registeredChassisNumbers.add("VWX24680");
        registeredChassisNumbers.add("YZA13568");
        registeredChassisNumbers.add("BCD86420");
        registeredChassisNumbers.add("EFG97531");
    }

    public boolean isVehicleRegistered(String chassisNumber) {
        return registeredChassisNumbers.contains(chassisNumber);
    }
}
