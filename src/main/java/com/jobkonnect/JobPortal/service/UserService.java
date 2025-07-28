package com.jobkonnect.JobPortal.service;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.ResponseDTO;
import com.jobkonnect.JobPortal.dto.UserDto;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    UserDto loginUser(LoginDto loginDto);

    ResponseDTO sendOtp(String email) throws Exception;

    ResponseDTO verifyOtp(String email, String otp);

    ResponseDTO changePassword(LoginDto loginDto);

}
