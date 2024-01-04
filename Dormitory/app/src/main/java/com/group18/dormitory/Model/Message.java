package com.group18.dormitory.Model;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Message {
    private String uid;
    private String content;
    private Timestamp addtime;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getAddtime() {
        return addtime;
    }

    public void setAddtime(Timestamp addtime) {
        this.addtime = addtime;
    }

    public Message( String content,String uid, Timestamp addtime) {
        this.uid = uid;
        this.content = content;
        this.addtime = addtime;
    }
    public Message(){
        //default constructor
    }

// Constructor, getters, and setters
}
