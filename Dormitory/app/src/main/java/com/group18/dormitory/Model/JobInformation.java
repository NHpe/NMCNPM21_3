package com.group18.dormitory.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class JobInformation {
    private String id;
    private String jobId;
    private ArrayList<String> employeeId;
    private HashMap<String, ArrayList<String>> schedule;

    public JobInformation(String jobId) {
        this.id = new Date().toString();
        this.jobId = jobId;
        this.employeeId = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("");
        temp.add("");
        temp.add("");
        this.schedule = new HashMap<>();
        for(int i = 0; i < 7; i++) {
            this.schedule.put(String.valueOf(i), temp);
        }
    }

    public JobInformation() {
    }

    public HashMap<String, ArrayList<String>> getSchedule() {
        return schedule;
    }

    public void setSchedule(HashMap<String, ArrayList<String>> schedule) {
        this.schedule = schedule;
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

    public ArrayList<String> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(ArrayList<String> employeeId) {
        this.employeeId = employeeId;
    }
}
