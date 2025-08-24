package com.jobkonnect.JobPortal.repository;

import com.jobkonnect.JobPortal.model.JobModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobModel, String> {
}
