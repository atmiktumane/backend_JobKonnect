package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.ResponseDTO;
import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.exception.EmailAlreadyExistsException;
import com.jobkonnect.JobPortal.exception.InvalidCredentialsException;
import com.jobkonnect.JobPortal.model.OtpModel;
import com.jobkonnect.JobPortal.model.Role;
import com.jobkonnect.JobPortal.model.UserModel;
import com.jobkonnect.JobPortal.repository.OtpRepository;
import com.jobkonnect.JobPortal.repository.UserRepository;
import com.jobkonnect.JobPortal.service.UserService;
import com.jobkonnect.JobPortal.utilities.Data;
import com.jobkonnect.JobPortal.utilities.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service  // This annotation is mandatory for Spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // inject the bean

    @Autowired
    private JavaMailSender mailSender;

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

    @Override
    public ResponseDTO sendOtp(String email) throws Exception {
//        // Testing
//        System.out.println("Email in API request params : "+ email);

        // Check if Email is present or not
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(()-> new InvalidCredentialsException("Invalid email"));

        // MimeMessage : return HTML Body in Message
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mailMessage, true);

        message.setTo(email);  // Set Receiver Email
        message.setSubject("Your OTP Code");  // Set Email Subject

        // Generate OTP from Utilities
        String generatedOtp = Utilities.generateOTP();

        // Create otp data object with Given Data (i.e., email, otpCode, currentTime)
        OtpModel otp = new OtpModel(email, generatedOtp, LocalDateTime.now());

        // Save otp data in database
        otpRepository.save(otp);

        // Username of receiver
        String username = user.getName();

        // Email Body - Call "Data" Utility Class
        message.setText(Data.otpEmailBody(generatedOtp, username),true);

        mailSender.send(mailMessage);

        return new ResponseDTO("OTP sent successfully.");


    }

}
