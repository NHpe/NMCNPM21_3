package com.group18.dormitory.Model;

import com.group18.dormitory.Model.Date;
public class Message {
    private String senderID;
    private String receiverID;
    private Date dateSend;
    private String data;

    public Message() {

    }

    public String getSenderID() {return senderID;}

    public void setSenderID(String senderID) {this.senderID = senderID;}

    public String getReceiverID() {return receiverID;}

    public void setReceiverID(String receiverID) {this.receiverID = receiverID;}

    public Date getDateSend() {return dateSend;}

    public void setDateSend(Date date) {this.dateSend = date;}

    public String getData() {return data;}

    public void setData(String data) {this.data = data;}
}
