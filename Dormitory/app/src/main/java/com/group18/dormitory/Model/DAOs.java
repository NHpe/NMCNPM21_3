package com.group18.dormitory.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class DAOs {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    String UserID;

    public FirebaseFirestore getDb () {return db;}

    public void createUserAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                    }
                });
    }

    public void addObjectToFirestore(String col, Object obj, OnResultListener l) {
        db.collection(col).add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                l.onResult(true);
            }
        });
    }

    public void addDataToDatabase(String collection, String id, Object data) {
        db.collection(collection).document(id).set(data);
    }

    public void addDataToDatabase(String collection, String id, Object data, OnResultListener l) {
        db.collection(collection).document(id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                l.onResult(true);
            }
        });
    }

    public String getCurrentUserId() {
        return auth.getUid();
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

    public void getCollection(String collection, OnCompleteRetrieveDataListener listener) {
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if(!querySnapshot.isEmpty()) {
                        List<String> result = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot :
                                querySnapshot) {
                            result.add(documentSnapshot.getId());
                        }
                        listener.onComplete(result);

                    }else {
                        listener.onComplete(null);
                    }
                }
            }
        });
    }

    public<T> void retrieveDataFromDatabase(String collection, String id, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        List<T> result = new ArrayList<>();
                        result.add(documentSnapshot.toObject(type));
                        listener.onComplete(result);
                    } else {
                        listener.onComplete(null);
                    }
                }
            }
        });
    }

    public<T> void retrieveDataFromDatabase(String collection, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if(!querySnapshot.isEmpty()) {
                        List<T> result = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot :
                                querySnapshot) {
                            result.add(documentSnapshot.toObject(type));
                        }
                        listener.onComplete(result);

                    }else {
                        listener.onComplete(null);
                    }
                }

            }
        });
    }

    public<T> void getNotificationFromDb(String collection, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if(!querySnapshot.isEmpty()) {
                        List<T> result = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot :
                                querySnapshot) {
                            result.add(documentSnapshot.toObject(type));
                        }
                        listener.onComplete(result);

                    }else {
                        listener.onComplete(null);
                    }
                }

            }
        });
    }

    public<T> void getRoomWithSpecific(String collection, String gender, int maxNumber, boolean furniture, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection)
                .whereEqualTo("gender", gender)
                .whereEqualTo("maxNumber", maxNumber)
                .whereEqualTo("furniture", furniture).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if(!querySnapshot.isEmpty()) {
                        List<T> result = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot :
                                querySnapshot) {
                            result.add(documentSnapshot.toObject(type));
                        }
                        listener.onComplete(result);

                    }else {
                        listener.onComplete(null);
                    }
                }

            }
        });
    }

    public void getRoomRegistrationId(String studentId, String roomId, OnCompleteRetrieveDataListener listener) {
        db.collection("RoomRegistrationInformation")
                .whereEqualTo("studentId", studentId)
                .whereEqualTo("roomId", roomId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if(!querySnapshot.isEmpty()) {
                                List<String> result = new ArrayList<>();
                                for (DocumentSnapshot documentSnapshot :
                                        querySnapshot) {
                                    result.add(documentSnapshot.getId());
                                }
                                listener.onComplete(result);

                            }else {
                                listener.onComplete(null);
                            }
                        }

                    }
                });
    }

    public void deleteDocument(String collection, String document) {
        db.collection(collection).document(document).delete();
    }

    public void signOut() {
        auth.signOut();
    }

    public void reAuthenticate(String password, OnResultListener listener) {
        FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            listener.onResult(true);
                        } else {
                            listener.onResult(false);
                        }
                    }
                });
    }

    public void changePassword(String password, OnResultListener l) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        l.onResult(true);
                    }
                });
    }

    public interface OnResultListener {
        public void onResult(boolean result);
    }

    public interface OnCompleteRetrieveDataListener {
        <T>void onComplete(List<T> list);
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

