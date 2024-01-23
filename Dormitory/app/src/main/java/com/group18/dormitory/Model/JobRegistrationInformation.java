package com.group18.dormitory.Model;

import java.util.Date;

public class JobRegistrationInformation {
    private String id;
    private String jobId;
    private String employeeId;

    public JobRegistrationInformation() {
    }

    public JobRegistrationInformation(String jobId, String employeeId) {
        this.id = new Date().toString();
        this.jobId = jobId;
        this.employeeId = employeeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
