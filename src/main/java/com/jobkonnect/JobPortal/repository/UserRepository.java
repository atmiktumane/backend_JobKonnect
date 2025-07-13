package com.jobkonnect.JobPortal.repository;

import com.jobkonnect.JobPortal.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
}
