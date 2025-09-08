package com.jobkonnect.JobPortal.dto;

import com.jobkonnect.JobPortal.model.ProfileModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private String id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String image;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certification> certifications;
    private List<String> savedJobs;

    public ProfileModel toEntity(){
        return new ProfileModel(this.id, this.email, this.jobTitle, this.company, this.location, this.about, this.image!=null? Base64.getDecoder().decode(this.image):null, this.skills, this.experiences, this.certifications, this.savedJobs);
    }

}
