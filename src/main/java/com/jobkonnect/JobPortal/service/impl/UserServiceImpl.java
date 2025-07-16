package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.exception.EmailAlreadyExistsException;
import com.jobkonnect.JobPortal.exception.InvalidCredentialsException;
import com.jobkonnect.JobPortal.model.Role;
import com.jobkonnect.JobPortal.model.UserModel;
import com.jobkonnect.JobPortal.repository.UserRepository;
import com.jobkonnect.JobPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // This annotation is mandatory for Spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // inject the bean

    @Override
    public UserDto registerUser(UserDto userDto){
        // Check if Email already exists
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists : "+userDto.getEmail());
        }

        UserModel user = new UserModel();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set Role (from request or default as APPLICANT)
        if(userDto.getRole() != null){
            user.setRole(userDto.getRole());
        } else{
            user.setRole(Role.APPLICANT); // default role
        }

        // Save user data in db
        UserModel savedUser = userRepository.save(user);

        // Prepare response body
        UserDto response = new UserDto();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    @Override
    public LoginDto loginUser(LoginDto loginDto){
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid email or password"));

        // Match Password
        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Prepare response body
        LoginDto response = new LoginDto();
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());

        return response;

    }

}
