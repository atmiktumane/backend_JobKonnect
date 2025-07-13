package com.jobkonnect.JobPortal.controller;

import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}
