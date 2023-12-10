package com.group18.dormitory.Model;

import com.group18.dormitory.Model.Date;
public class Job {
    private String id;
    private String name;
    private float rate;
    private String requirement;
    private Date[] schedule;
    private String type;

    public Job() {

    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public float getRate() {return rate;}

    public void setRate(float rate) {this.rate = rate;}

    public String getRequirement() {return requirement;}

    public void setRequirement(String requirement) {this.requirement = requirement;}

    public Date[] getSchedule() {return schedule;}

    public void setSchedule(Date[] schedule) {this.schedule = schedule;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}
}
