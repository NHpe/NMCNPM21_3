package com.group18.dormitory.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Model.UserInformation;

public class DAOs {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    String UserID;



    public void createUserAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                    }
                });
    }

    public void signInWithEmailAndPassword(String email, String password, OnResultListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            listener.onResult(true);
                        } else {
                            listener.onResult(false);
                        }
                    }
                });
    }

    public UserInformation getUser() {
        DocumentReference documentReference = db.collection("UserInformation").document("y1z2eRMeaeujfBAGVumf");
        UserInformation user = new UserInformation();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String id = document.getString("id");
                        String fullName = document.getString("fullName");
                        String birthday = document.getString("birthday");
                        String gender = document.getString("gender");
                        String email = document.getString("email");
                        String phoneNumber = document.getString("phoneNumber");
                        String citizendId = document.getString("citizenId");
                        String address = document.getString("address");

                        user.setId(id);
                        user.setFullName(fullName);
                        user.setBirthday(birthday);
                        user.setGender(gender);
                        user.setEmail(email);
                        user.setPhoneNumber(phoneNumber);
                        user.setCitizenId(citizendId);
                        user.setAddress(address);
                    } else {

                    }
                } else {

                }
            }
        });
        return user;
    }

    public interface OnResultListener {
        public void onResult(Boolean result);
    }


    private static DAOs instance;
    private DAOs() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //UserID = auth.getCurrentUser().getUid();
    }
    public static DAOs getInstance() {
        if(instance == null) {
            instance = new DAOs();
        }
        return instance;
    }

}

