package com.group18.dormitory.Model;

import com.group18.dormitory.Model.Date;

public class Issue {
    private String id;
    private String tilte;
    private String message;
    private Date date;

    public Issue() {

    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTilte() {return tilte;}

    public void setTilte(String tilte) {this.tilte = tilte;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
