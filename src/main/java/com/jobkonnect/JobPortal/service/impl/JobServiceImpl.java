package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.JobDTO;
import com.jobkonnect.JobPortal.exception.ResourceNotFoundException;
import com.jobkonnect.JobPortal.repository.JobRepository;
import com.jobkonnect.JobPortal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("jobService")
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobDTO postJob(JobDTO jobDTO) {
        jobDTO.setPostTime(LocalDateTime.now());

        // Save in Database
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJobs() {
        // Return All Job List from DB
        return jobRepository.findAll().stream().map((x)->x.toDTO()).toList();
    }

    @Override
    public JobDTO getJob(String id) {
        // Return Job by ID from DB, if found else throw exception
        return jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job not found")).toDTO();
    }
}
