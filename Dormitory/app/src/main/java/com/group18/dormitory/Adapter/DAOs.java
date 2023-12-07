package com.group18.dormitory.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.group18.dormitory.Model.UserInformation;

public class DAOs {

    private FirebaseAuth auth;



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






    public interface OnResultListener {
        public void onResult(Boolean result);
    }


    private static DAOs instance;
    private DAOs() {
        auth = FirebaseAuth.getInstance();
    }
    public static DAOs getInstance() {
        if(instance == null) {
            instance = new DAOs();
        }
        return instance;
    }

}

