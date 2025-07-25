package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.LoginDto;
import com.jobkonnect.JobPortal.dto.ResponseDTO;
import com.jobkonnect.JobPortal.dto.UserDto;
import com.jobkonnect.JobPortal.exception.EmailAlreadyExistsException;
import com.jobkonnect.JobPortal.exception.InvalidCredentialsException;
import com.jobkonnect.JobPortal.exception.ResourceNotFoundException;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public ResponseDTO verifyOtp(String email, String otp) {
        OtpModel otpEntity = otpRepository.findById(email).orElseThrow(()-> new ResourceNotFoundException("OTP not found"));

        if(!otpEntity.getOtpCode().equals(otp)){
            throw new ResourceNotFoundException("OTP is Incorrect");
        }

        return new ResponseDTO("OTP is successfully verified.");
    }

    @Override
    public ResponseDTO changePassword(LoginDto loginDto) {
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new InvalidCredentialsException("User not found."));

        // Update new Password (encrypted)
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));

        // Save in DB
        userRepository.save(user);

        return new ResponseDTO("Password changed successfully.");
    }

    // Expire OTP - Job Scheduling (Check Expired OTPs every Minute and delete them)
    @Scheduled(fixedRate = 60000)  // 60 seconds - Run below function every Minute automatically
    public void removeExpiredOTPs(){
        // Current Time minus 5 minutes
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);

        // Store Expired OTPs in List - OTPs having 5 minutes Expiry Time
        List<OtpModel> expiredOTPs = otpRepository.findByCreationTimeBefore(expiryTime);

        // Delete all Expired OTPs from Database
        if(!expiredOTPs.isEmpty()){
            otpRepository.deleteAll(expiredOTPs);
            System.out.println("Removed "+ expiredOTPs.size() +" expired OTPs. ");
        }
    }

}
