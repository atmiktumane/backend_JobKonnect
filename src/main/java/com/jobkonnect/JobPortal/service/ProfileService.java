package com.jobkonnect.JobPortal.service;

import com.jobkonnect.JobPortal.dto.ProfileDTO;

public interface ProfileService {
    public String createProfile(String email);

    public ProfileDTO getProfile(String id);
    public ProfileDTO updateProfile(ProfileDTO profileDTO);
}
