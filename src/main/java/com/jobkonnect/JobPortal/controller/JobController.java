package com.jobkonnect.JobPortal.controller;

import com.jobkonnect.JobPortal.dto.ApplicantDTO;
import com.jobkonnect.JobPortal.dto.JobDTO;
import com.jobkonnect.JobPortal.dto.ResponseDTO;
import com.jobkonnect.JobPortal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/v1/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    // POST - Job
    @PostMapping("/post-job")
    public ResponseEntity<JobDTO> postJob(@RequestBody JobDTO jobDTO){
        return new ResponseEntity<>(jobService.postJob(jobDTO), HttpStatus.CREATED);
    }

    // GET - All Jobs
    @GetMapping("/all-jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    // GET - Particular Job by id
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable String id) {
        return new ResponseEntity<>(jobService.getJob(id), HttpStatus.OK);
    }

    // POST - Apply Job
    @PostMapping("/apply/{id}")
    public ResponseEntity<ResponseDTO> applyJob(@PathVariable String id, @RequestBody ApplicantDTO applicantDTO){
        jobService.applyJob(id, applicantDTO);
        return new ResponseEntity<>(new ResponseDTO("Applied Job Successfully"), HttpStatus.OK);
    }

}
