package com.jobkonnect.JobPortal.model;

import com.jobkonnect.JobPortal.dto.Certification;
import com.jobkonnect.JobPortal.dto.Experience;
import com.jobkonnect.JobPortal.dto.ProfileDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.List;

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

    public ProfileDTO toDTO(){
        return new ProfileDTO(this.id, this.email, this.jobTitle, this.company, this.location, this.about, this.image!=null? Base64.getEncoder().encodeToString(this.image):null, this.skills, this.experiences, this.certifications);
    }

    public ProfileModel() {}
    public ProfileModel(String id, String email, String jobTitle, String company, String location, String about, byte[] image, List<String> skills, List<Experience> experiences, List<Certification> certifications) {
        this.id = id;
        this.email = email;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.about = about;
        this.image = image;
        this.skills = skills;
        this.experiences = experiences;
        this.certifications = certifications;
    }

    // Getter & Setter functions

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
