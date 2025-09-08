package com.jobkonnect.JobPortal.model;

import com.jobkonnect.JobPortal.dto.ApplicantDTO;
import com.jobkonnect.JobPortal.dto.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantModel {
    @Id
    private String applicantId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private byte[] resume;
    private String coverLetter;
    private LocalDateTime timestamp;
    private ApplicationStatus applicationStatus;

    public ApplicantDTO toDTO(){
        return new ApplicantDTO(this.applicantId, this.name, this.email, this.phone, this.website, this.resume!=null? Base64.getEncoder().encodeToString(this.resume):null, this.coverLetter, this.timestamp, this.applicationStatus);
    }
}
