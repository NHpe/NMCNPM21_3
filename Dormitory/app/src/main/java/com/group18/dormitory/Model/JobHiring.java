package com.group18.dormitory.Model;

import java.util.Date;

public class JobHiring {
    private String id;
    private String ownerId;
    private String jobPosition;
    private String storeName;
    private String storePosition;
    private String content;
    private String salary;
    private Date datePost;

    public JobHiring(String id, String ownerId, String jobPosition, String storeName, String storePosition, Date datePost) {
        this.id = id;
        this.ownerId = ownerId;
        this.jobPosition = jobPosition;
        this.storeName = storeName;
        this.storePosition = storePosition;
        this.datePost = datePost;
    }

    public JobHiring(String ownerId, String jobPosition, String storeName, String storePosition, String content, String salary) {
        this.ownerId = ownerId;
        this.jobPosition = jobPosition;
        this.storeName = storeName;
        this.storePosition = storePosition;
        this.content = content;
        this.salary = salary;
        this.datePost = new Date();
        this.id = this.datePost.toString();
    }

    public JobHiring() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStorePosition() {
        return storePosition;
    }

    public void setStorePosition(String storePosition) {
        this.storePosition = storePosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }
}
