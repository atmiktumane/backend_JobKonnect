package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.model.UserModel;
import com.jobkonnect.JobPortal.repository.UserRepository;
import com.jobkonnect.JobPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // This annotation is mandatory for Spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // Conversion Helpers
    private UserDto convertToDto(UserModel userModel){
        return new UserDto(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                userModel.getPassword()
        );
    }

    private UserModel convertToEntity(UserDto userDto){
        UserModel user = new UserModel();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }

    @Override
    public UserDto registerUser(UserDto userDto){
        UserModel user = convertToEntity(userDto);
        UserModel savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

}
