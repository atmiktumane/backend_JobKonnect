package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.ProfileDTO;
import com.jobkonnect.JobPortal.exception.ResourceNotFoundException;
import com.jobkonnect.JobPortal.model.ProfileModel;
import com.jobkonnect.JobPortal.repository.ProfileRepository;
import com.jobkonnect.JobPortal.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public String createProfile(String email){
        ProfileModel profile = new ProfileModel();

        profile.setEmail(email);
        profile.setSkills(new ArrayList<>());
        profile.setExperiences((new ArrayList<>()));
        profile.setCertifications((new ArrayList<>()));

        profileRepository.save(profile);

        return profile.getId();
    }

    @Override
    public ProfileDTO getProfile(String id) {
        // Find Profile By Id and return Profile details
        return profileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Profile not found")).toDTO();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        // Find Profile By ID (present in profileDTO)
        profileRepository.findById(profileDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("Profile not found"));

        // save profile data in db
        profileRepository.save(profileDTO.toEntity());

        return profileDTO;
    }
}
