package com.uokse.fuelmaster.dto.Response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OwnerUpdateDTO {

    @NotNull(message = "Owner ID is required")
    @Positive(message = "Owner ID must be a positive number")
    private Long ownerId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}