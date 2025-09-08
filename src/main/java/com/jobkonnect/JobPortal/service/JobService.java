package com.jobkonnect.JobPortal.service;

import com.jobkonnect.JobPortal.dto.ApplicantDTO;
import com.jobkonnect.JobPortal.dto.JobDTO;

import java.util.List;

public interface JobService {

    public JobDTO postJob(JobDTO jobDTO);

    public List<JobDTO> getAllJobs();

    public JobDTO getJob(String id);
    public void  applyJob(String id, ApplicantDTO applicantDTO);
}
