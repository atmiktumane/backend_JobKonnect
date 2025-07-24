package com.jobkonnect.JobPortal.repository;

import com.jobkonnect.JobPortal.model.OtpModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OtpRepository extends MongoRepository<OtpModel, String> {
    List<OtpModel> findByCreationTimeBefore(LocalDateTime expiryTime);
}
