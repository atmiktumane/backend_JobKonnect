package com.jobkonnect.JobPortal.dto;

import java.time.LocalDateTime;

public class Certification {
    private String name;
    private String company;
    private LocalDateTime issueDate;
    private String certificateId;

    public Certification(){}

    public Certification(String name, String company, LocalDateTime issueDate, String certificateId) {
        this.name = name;
        this.company = company;
        this.issueDate = issueDate;
        this.certificateId = certificateId;
    }

    // Getter & Setter functions
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}
