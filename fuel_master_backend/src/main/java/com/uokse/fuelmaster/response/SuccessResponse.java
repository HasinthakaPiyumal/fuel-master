package com.uokse.fuelmaster.response;

import java.util.Optional;

public class SuccessResponse {
    private String message;
    private int status;
    private Object data;

    public SuccessResponse(String message, Boolean status, Object data) {
        this.status = status?200:500;
        this.message = message;
        this.data = data;
    }

    public SuccessResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
