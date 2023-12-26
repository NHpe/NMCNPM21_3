package com.group18.dormitory.Model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.DAOs;

public class UserInformation {
    private static UserInformation instance;
    private String id;
    private String fullName;
    private String birthday;
    private String gender;
    private String email;
    private String phoneNumber;
    private String citizenId;
    private String address;
    private FirebaseFirestore db = DAOs.getInstance().getDb();

    public UserInformation() {
    }

    public static UserInformation getInstance() {
        if(instance == null) {
            instance = new UserInformation();
        }
        return instance;
    }

    public UserInformation getUser() {
        // Thay Test thành UserID để lấy dữ liệu khác
        // UserID sẽ có khi có tài khoản đăng nhập - chắc thế
        DocumentReference documentReference = db.collection("UserInformation").document("Test");

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                instance = documentSnapshot.toObject(UserInformation.class);
            }
        });
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
