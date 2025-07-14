package com.jobkonnect.JobPortal.dto;

public class LoginDto {
    private String email;
    private String password;

    public LoginDto(){}

    public LoginDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getter & Setter functions

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
