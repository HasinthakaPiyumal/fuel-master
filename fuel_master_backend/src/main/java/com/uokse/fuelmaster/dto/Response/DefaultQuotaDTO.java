package com.uokse.fuelmaster.dto.Response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DefaultQuotaDTO {

    @NotNull(message = "Default quota is required")
    @Positive(message = "Default quota must be a positive number")
    private Long defaultQuota;

    public Long getDefaultQuota() {
        return defaultQuota;
    }

    public void setDefaultQuota(Long defaultQuota) {
        this.defaultQuota = defaultQuota;
    }
}
