package com.group18.dormitory.Model;

import java.util.Date;

public class Issue {
    private String id;
    private String senderId;
    private String title;
    private String message;
    private Date date;
    private Boolean status;

    public Issue() {

    }

    public Issue(String id, String senderId, String title, String message, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.title = title;
        this.message = message;
        this.date = date;
        this.status = false;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
