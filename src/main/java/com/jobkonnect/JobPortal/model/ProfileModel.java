package com.jobkonnect.JobPortal.model;

import com.jobkonnect.JobPortal.dto.Certification;
import com.jobkonnect.JobPortal.dto.Experience;
import com.jobkonnect.JobPortal.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class ProfileModel {
    @Id
    private String id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private byte[] image;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certification> certifications;
    private List<String> savedJobs;

    public ProfileDTO toDTO(){
        return new ProfileDTO(this.id, this.email, this.jobTitle, this.company, this.location, this.about, this.image!=null? Base64.getEncoder().encodeToString(this.image):null, this.skills, this.experiences, this.certifications, this.savedJobs);
    }


}
