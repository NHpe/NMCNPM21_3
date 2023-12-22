package com.group18.dormitory.Model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.DAOs;

import java.util.Date;

public class Bill {
    private static Bill instance;
    private FirebaseFirestore db = DAOs.getInstance().getDb();
    private String[] userID;
    private String roomID;
    private Date dateStart;
    private Date dateEnd;
    private float electric;
    private float water;
    private float service;

    public Bill() {

    }

    public static Bill getInstance() {
        if(instance == null) {
            instance = new Bill();
        }
        return instance;
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

    public Bill getBill() {
        DocumentReference documentReference = db.collection("Bill").document("0");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                instance = documentSnapshot.toObject(Bill.class);
            }
        });
        return instance;
    }
}
