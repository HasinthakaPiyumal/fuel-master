package com.uokse.fuelmaster.response;

public class SuccessResponse {
    private String message;
    private Boolean status;
    private Object data;

    public SuccessResponse(String message, Boolean status, Object data) {
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
