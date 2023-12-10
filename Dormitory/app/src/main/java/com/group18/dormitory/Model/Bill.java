package com.group18.dormitory.Model;

import com.group18.dormitory.Model.Date;

public class Bill {
    private String[] userID;
    private String roomID;
    private Date dateStart;
    private Date dateEnd;
    private float electric;
    private float water;
    private float service;

    public Bill() {

    }

    public String[] getUserID() {return userID;}

    public void setUserID(String[] userID) {this.userID = userID;}

    public String getRoomID() {return  roomID;}

    public void setRoomID(String roomID) {this.roomID = roomID;}

    public Date getDateStart() {return dateStart;}

    public void setDateStart(Date date) {this.dateStart = date;}

    public Date getDateEnd() {return dateEnd;}

    public void setDateEnd(Date date) {this.dateEnd = date;}

    public float getElectric() {return electric;}

    public void setElectric(float electric) {this.electric = electric;}

    public float getWater() {return water;}

    public void setWater(float water) {this.water = water;}

    public float getService() {return service;}

    public void setService(float service) {this.service = service;}
}
