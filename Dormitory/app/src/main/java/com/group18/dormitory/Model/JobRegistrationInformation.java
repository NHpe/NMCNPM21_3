package com.group18.dormitory.Model;

public class JobRegistrationInformation {
    private String jobId;
    private String employeeId;

    public JobRegistrationInformation() {
    }

    public JobRegistrationInformation(String jobId, String employeeId) {
        this.jobId = jobId;
        this.employeeId = employeeId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String roomId) {
        this.jobId = jobId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
