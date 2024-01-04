package com.group18.dormitory;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Adapter.ChatAdapter;
import com.group18.dormitory.Model.ChatRoom;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Message;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment {
    private Context context;
    private View container;
    private CircleImageView imgViewAvatar;
    private ImageButton btnCallBack;
    private TextView txtName;
    private RecyclerView chatView;
    private EditText edtMessage;
    private Button btnSend;
    private ArrayList<Message> msgArrayList;
    private String chatroomID;
    private ChatRoom chatRoom;
    ChatAdapter adapter;
    private View view;

    public ChatFragment() {
        //Empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, null);
        initiate();
        return view;
    }

    private void initiate() {
        imgViewAvatar =  view.findViewById(R.id.imgViewAvatar);
        txtName = view.findViewById(R.id.txtName);
        chatView = view.findViewById(R.id.chatView);
        edtMessage =  view.findViewById(R.id.edtMessage);
        btnSend =  view.findViewById(R.id.btnSend);
        container = view.findViewById(R.id.container);

        String targetId = getArguments().getString("ID");
        String currentId = DAOs.getInstance().getCurrentUserId();

        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", targetId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation targetInfo = (UserInformation) list.get(0);
                txtName.setText(targetInfo.getFullName());
                Glide.with(view).load(targetInfo.getAvatar())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(imgViewAvatar);



                chatroomID = DAOs.getChatRoomID(currentId,targetId);

                msgArrayList = new ArrayList<Message>();

                adapter = new ChatAdapter(context, msgArrayList, targetInfo.getAvatar());
                chatView.setAdapter(adapter);
                chatView.setLayoutManager(new LinearLayoutManager(context));
                getOrCreateChatRoom(currentId, targetId);
                EventChangeListener();
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = edtMessage.getText().toString().trim();
                        if(msg.isEmpty())
                            return;
                        sendMessageToUsers(msg, currentId, targetId);
                    }
                });
            }
        });

        imgViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", targetId);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_chatFragment_to_userInformationFragment, bundle);
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void sendMessageToUsers(String message, String currentId, String targetId) {
        chatRoom.setLastMsgTimeStamp(Timestamp.now());
        chatRoom.setLastMsgID(currentId);
        DAOs.getChatRoomRef(chatroomID).set(chatRoom);

        Message msg = new Message(message,currentId,Timestamp.now());
        DAOs.getMsgRef(chatroomID).add(msg);
        edtMessage.setText("");
    }

    void getOrCreateChatRoom(String currentId, String targetId){
        DAOs.getChatRoomRef(chatroomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    chatRoom = task.getResult().toObject(ChatRoom.class);
                    if(chatRoom==null){
                        chatRoom = new ChatRoom(
                                chatroomID,
                                Arrays.asList(targetId,currentId),
                                Timestamp.now(),
                                ""
                        );
                    }
                    DAOs.getChatRoomRef(chatroomID).set(chatRoom);
                }
            }
        });
    }
    private void EventChangeListener() {
        // Listen for real-time updates on the chat messages
        FirebaseFirestore.getInstance().collection("ChatRoom").document(chatroomID).collection("chats")
                .orderBy("addtime", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error getting documents.", error);
                            return;
                        }
                        if (querySnapshot != null) {
                            for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:{
                                        // New message added
                                        Message msg = dc.getDocument().toObject(Message.class);
                                        msgArrayList.add(msg);
                                        adapter.notifyItemInserted(msgArrayList.size() - 1);
                                        chatView.scrollToPosition(adapter.getItemCount() - 1);
                                        break;
                                    }

                                    // Handle other cases if needed (MODIFIED, REMOVED)
                                }
                            }
                        }
                    }
                });
    }


}