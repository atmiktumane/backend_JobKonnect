package com.jobkonnect.JobPortal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "otp")
public class OtpModel {
    @Id
    private String email;
    private String otpCode;
    private LocalDateTime creationTime;

    public OtpModel(){}

    public OtpModel(String email, String otpCode, LocalDateTime creationTime) {
        this.email = email;
        this.otpCode = otpCode;
        this.creationTime = creationTime;
    }

    // Getter & Setter Functions
    public void setEmail(String email){ // Set email function
        this.email=email;
    }
    public String getEmail(){ // Get email function
        return email;
    }

    public void setOtpCode(String otpCode){ // Set otpCode function
        this.otpCode = otpCode;
    }
    public String getOtpCode(){ // Get otpCode function
        return otpCode;
    }

    public void setCreationTime(LocalDateTime creationTime){ // Set creationTime function
        this.creationTime = creationTime;
    }
    public LocalDateTime getCreationTime(){ // Get creationTime function
        return creationTime;
    }

}
