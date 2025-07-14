package com.jobkonnect.JobPortal.dto;

import com.jobkonnect.JobPortal.model.Role;

public class LoginDto {
    private String email;
    private String password;
    private Role role;

    public LoginDto(){}

    public LoginDto(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
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

    public void setRole(Role role){
        this.role = role;
    }
    public Role getRole(){
        return role;
    }
}
