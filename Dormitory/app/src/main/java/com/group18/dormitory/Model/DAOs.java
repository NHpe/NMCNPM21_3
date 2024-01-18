package com.group18.dormitory.Model;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


public class DAOs {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
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

    public static DocumentReference getChatRoomRef(String chatroomID){
        return FirebaseFirestore.getInstance().collection("ChatRoom").document(chatroomID);
    }
    public static CollectionReference getMsgRef(String chatroomID){
        return getChatRoomRef(chatroomID).collection("chats");
    }

    public static String getChatRoomID(String userID1,String userID2){
        if(userID1.hashCode()<userID2.hashCode()){
            return userID1 + "_" + userID2;
        }else {
            return userID2 + "_" + userID1;
        }
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

    public void uploadUserAvatar(String userId, Uri imageUri, String imageType, OnResultUploadAvatarListener listener) {
        String folder = "Avatar";
        StorageReference storageRef = storage.getReference(folder).child(userId + "." + imageType);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        listener.onResult(uri);
                                    }
                                });
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

    public<T> void getIssueFromDB(String collection, Class<T> type, OnCompleteRetrieveDataListener listener) {
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

    public<T> void getIssueWithSenderId(String collection, String senderId, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection)
                .whereEqualTo("senderId", senderId)
                .orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println(task.getException() + "\n\n\n");
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

    public<T> void getIssueWithSpecific(String collection, String pos, boolean status, Class<T> type, OnCompleteRetrieveDataListener listener) {
        db.collection(collection)
                .whereEqualTo("title", pos)
                .whereEqualTo("status", status).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    public interface OnResultUploadAvatarListener {
        void onResult(Uri Uri);
    }


    private static DAOs instance;
    private DAOs() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        //UserID = auth.getCurrentUser().getUid();
    }
    public static DAOs getInstance() {
        if(instance == null) {
            instance = new DAOs();
        }
        return instance;
    }

}

