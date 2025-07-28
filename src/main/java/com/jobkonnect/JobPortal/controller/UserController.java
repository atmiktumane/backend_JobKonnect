package com.jobkonnect.JobPortal.controller;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.ResponseDTO;
import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // POST - Register
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    // POST - Login
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.loginUser(loginDto));
    }

    // Send OTP
    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "Email is invalid.") String email) throws Exception {
//        userService.sendOtp(email);
//        return new ResponseEntity<>(new ResponseDTO("OTP sent successfully."), HttpStatus.OK);

        return new ResponseEntity<>(userService.sendOtp(email), HttpStatus.OK);
    }

    // Verify OTP
    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message = "Email is invalid.") String email, @PathVariable @Pattern(regexp="^[0-9]{6}$", message="OTP is invalid.") String otp) {

        return new ResponseEntity<>(userService.verifyOtp(email, otp), HttpStatus.OK);
    }

    // POST - Change Password
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.changePassword(loginDto), HttpStatus.OK);
    }
}
