package com.group18.dormitory.Model;

import java.util.Date;

public class Notification {
    private String id;
    private String title;
    private String content;
    private Date date;

    public Notification() {

    }

    public Notification(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = new java.util.Date();
        this.id = String.valueOf(date);
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTilte(String title) {this.title = title;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
