package com.jobkonnect.JobPortal.service;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.UserDto;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    LoginDto loginUser(LoginDto loginDto);
}
