package com.jobkonnect.JobPortal.repository;

import com.jobkonnect.JobPortal.model.ProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<ProfileModel, String> {
}
