package com.group18.dormitory.Model;

import java.util.Date;

public class Notification {
    private String id;
    private String title;
    private String message;
    private Date date;

    public Notification() {

    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTilte(String title) {this.title = title;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
