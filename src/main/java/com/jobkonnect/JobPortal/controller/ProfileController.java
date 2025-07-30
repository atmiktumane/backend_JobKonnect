package com.jobkonnect.JobPortal.controller;

import com.jobkonnect.JobPortal.dto.ProfileDTO;
import com.jobkonnect.JobPortal.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    // Get Profile Details
    @GetMapping("/details/{id}")
    public ResponseEntity<ProfileDTO> verifyOtp(@PathVariable String id) {

        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
    }

    // Update Profile
    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> verifyOtp(@RequestBody ProfileDTO profileDTO) {

        return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
    }
}
