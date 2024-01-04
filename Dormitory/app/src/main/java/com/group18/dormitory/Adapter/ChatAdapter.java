package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Message;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Context context;
    ArrayList<Message> MessageArrayList;
    View view;
    String targetAvatar;

    public ChatAdapter(Context context, ArrayList<Message> messageArrayList, String targetAvatar) {
        this.context = context;
        this.MessageArrayList = messageArrayList;
        this.targetAvatar = targetAvatar;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_chat_view, parent, false);
        return new ChatAdapter.ChatViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        Message msg = MessageArrayList.get(position);
        Date date = msg.getAddtime().toDate();

        // Format the Date to display only the time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM", Locale.getDefault());
        String formattedTime = sdf.format(date);

        if (msg.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.recievedMessage.setVisibility(View.GONE);
            holder.sentMessage.setVisibility(View.VISIBLE);
            holder.txtSentMessage.setText(msg.getContent());
            holder.txtDateTime.setText(formattedTime);
            holder.sentMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.txtDateTime.getVisibility() == View.GONE) {
                        holder.txtDateTime.setVisibility(View.VISIBLE);
                    } else {
                        holder.txtDateTime.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            holder.sentMessage.setVisibility(View.GONE);
            holder.recievedMessage.setVisibility(View.VISIBLE);
            holder.txtRecieveMessage.setText(msg.getContent());
            holder.txtDateTime.setText(formattedTime);
            Glide.with(view).load(targetAvatar)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(holder.imgProfile);
            holder.recievedMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.txtDateTime.getVisibility() == View.GONE) {
                        holder.txtDateTime.setVisibility(View.VISIBLE);
                    } else {
                        holder.txtDateTime.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return MessageArrayList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        View sentMessage, recievedMessage;
        TextView txtSentMessage, txtRecieveMessage;
        TextView txtDateTime;
        ImageView imgProfile;

        public ChatViewHolder(@NonNull View custom_chat_view) {
            super(custom_chat_view);
            sentMessage = custom_chat_view.findViewById(R.id.layout_sent_message);
            recievedMessage = custom_chat_view.findViewById(R.id.layout_recieved_message);
            txtSentMessage = custom_chat_view.findViewById(R.id.txtSentMessage);
            txtDateTime = custom_chat_view.findViewById(R.id.txtDateTime);
            txtRecieveMessage = custom_chat_view.findViewById(R.id.txtRecieveMessage);
            imgProfile = custom_chat_view.findViewById(R.id.imgProfile);
        }
    }
}