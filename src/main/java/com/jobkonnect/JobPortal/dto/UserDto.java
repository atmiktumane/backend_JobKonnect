package com.jobkonnect.JobPortal.dto;

import com.jobkonnect.JobPortal.model.Role;

public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;  // Enum field

    public UserDto() {}

    public UserDto(String id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getter & Setter functions

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }
}
