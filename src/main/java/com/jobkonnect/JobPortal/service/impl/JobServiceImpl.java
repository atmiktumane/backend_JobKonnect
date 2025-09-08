package com.jobkonnect.JobPortal.service.impl;

import com.jobkonnect.JobPortal.dto.ApplicantDTO;
import com.jobkonnect.JobPortal.dto.ApplicationStatus;
import com.jobkonnect.JobPortal.dto.JobDTO;
import com.jobkonnect.JobPortal.exception.ResourceNotFoundException;
import com.jobkonnect.JobPortal.model.ApplicantModel;
import com.jobkonnect.JobPortal.model.JobModel;
import com.jobkonnect.JobPortal.repository.JobRepository;
import com.jobkonnect.JobPortal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public void applyJob(String id, ApplicantDTO applicantDTO) {
        JobModel job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job not found"));

        List<ApplicantModel> applicants = job.getApplicants();

        if(applicants==null) applicants = new ArrayList<>();

        // Check whether user/applicant Already applied previously
        boolean alreadyApplied = applicants.stream()
                .anyMatch(x -> x.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new ResourceNotFoundException("Job Applied Already");
        }

        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());

        job.setApplicants(applicants);

        jobRepository.save(job);

    }
}
