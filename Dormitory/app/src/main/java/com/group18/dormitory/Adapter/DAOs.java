package com.group18.dormitory.Adapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Model.Bill;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.UserInformation;


public class DAOs {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    String UserID;
    UserInformation userInformation;
    Room room;
    Bill bill;



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
        // Thay Test thành UserID để lấy dữ liệu khác
        // UserID sẽ có khi có tài khoản đăng nhập - chắc thế
        DocumentReference documentReference = db.collection("UserInformation").document("Test");

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userInformation = documentSnapshot.toObject(UserInformation.class);
            }
        });
        return userInformation;
    }

    public Room getRoom() {
        DocumentReference documentReference = db.collection("Room").document("0");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                room = documentSnapshot.toObject(Room.class);
            }
        });
        return room;
    }

    public Bill getBill() {
        DocumentReference documentReference = db.collection("Bill").document("0");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                bill = documentSnapshot.toObject(Bill.class);
            }
        });
        return bill;
    }

    public interface OnResultListener {
        public void onResult(Boolean result);
    }


    private static DAOs instance;
    private DAOs() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userInformation = UserInformation.getInstance();
        room = Room.getInstance();
        bill = Bill.getInstance();
        //UserID = auth.getCurrentUser().getUid();
    }
    public static DAOs getInstance() {
        if(instance == null) {
            instance = new DAOs();
        }
        return instance;
    }

}

