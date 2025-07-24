package com.jobkonnect.JobPortal.repository;

import com.jobkonnect.JobPortal.model.OtpModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpRepository extends MongoRepository<OtpModel, String> {
}
