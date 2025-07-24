package com.jobkonnect.JobPortal.dto;

public class ResponseDTO {
    String message;

    public ResponseDTO(){}

    public ResponseDTO(String message) {
        this.message = message;
    }

    // Getter & Setter functions
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
