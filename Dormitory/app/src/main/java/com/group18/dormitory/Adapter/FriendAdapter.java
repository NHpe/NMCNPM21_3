package com.group18.dormitory.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.Message;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FriendAdapter  extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private View view;
    private FriendAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(FriendAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FriendAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_friend_list_recycler_view, parent, false);
        return new FriendAdapter.FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.FriendViewHolder holder, int position) {
        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", items.get(position), UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation friendInfo = (UserInformation) list.get(0);
                String targetId = friendInfo.getId();
                String currentId = DAOs.getInstance().getCurrentUserId();
                String chatRoomId = DAOs.getChatRoomID(currentId, targetId);

                FirebaseFirestore.getInstance().collection("ChatRoom").document(chatRoomId).collection("chats")
                        .orderBy("addtime", Query.Direction.DESCENDING)
                        .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()) {
                                            QuerySnapshot querySnapshot = task.getResult();
                                            if(!querySnapshot.isEmpty()) {
                                                Message msg = querySnapshot.getDocuments().get(0).toObject(Message.class);
                                                if(msg.getUid().equals(currentId)) {
                                                    holder.message.setText("Bạn: " + msg.getContent());
                                                } else {
                                                    holder.message.setText(msg.getContent());
                                                }


                                                Date date = msg.getAddtime().toDate();
                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                                String formattedTime = sdf.format(date);
                                                holder.lastTime.setText(formattedTime);
                                            } else {
                                                holder.message.setVisibility(View.GONE);
                                                holder.lastTime.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                });

                holder.name.setText(friendInfo.getFullName());
                Glide.with(view).load(friendInfo.getAvatar()).error(R.drawable.ic_person).into(holder.avatar);

                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(items.get(holder.getAdapterPosition()));
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;

        TextView name;
        TextView message;
        TextView lastTime;
        View container;

        public FriendViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            name = view.findViewById(R.id.name);
            message = view.findViewById(R.id.message);
            lastTime = view.findViewById(R.id.lastTime);
            avatar = view.findViewById(R.id.avatar);
        }
    }
}
