package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private View view;
    private FriendRequestAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FriendRequestAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FriendRequestAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendRequestAdapter.FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_friend_request_recycler_view, parent, false);
        return new FriendRequestAdapter.FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.FriendRequestViewHolder holder, int position) {

        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", items.get(position), UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation targetUser = (UserInformation) list.get(0);

                holder.txtName.setText(targetUser.getFullName());
                Glide.with(view).load(targetUser.getAvatar()).error(R.drawable.ic_person).into(holder.imgAvatar);

                String currentId = DAOs.getInstance().getCurrentUserId();
                String targetId = items.get(holder.getAdapterPosition());
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(targetId);
                        }
                    }
                });
                holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DAOs.getInstance().retrieveDataFromDatabase("FriendList", targetId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                            @Override
                            public <T> void onComplete(List<T> list) {
                                FriendList friendList = (FriendList) list.get(0);
                                friendList.getFriendsId().add(currentId);
                                DAOs.getInstance().addDataToDatabase("FriendList", targetId, friendList);
                                DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                                    @Override
                                    public <T> void onComplete(List<T> list) {
                                        FriendList friendList = (FriendList) list.get(0);
                                        friendList.getFriendsId().add(targetId);
                                        friendList.getFriendRequestId().remove(targetId);
                                        DAOs.getInstance().addDataToDatabase("FriendList", currentId, friendList);


                                        items.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                        notifyItemRangeChanged(holder.getAdapterPosition(),
                                                items.size() - holder.getAdapterPosition());
                                    }
                                });
                            }
                        });
                    }
                });
                holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                            @Override
                            public <T> void onComplete(List<T> list) {
                                FriendList friendList = (FriendList) list.get(0);
                                friendList.getFriendRequestId().remove(targetId);
                                DAOs.getInstance().addDataToDatabase("FriendList", currentId, friendList);


                                items.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),
                                        items.size() - holder.getAdapterPosition());
                            }
                        });
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

    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName;
        Button btnAccept;
        Button btnDecline;
        View container;

        public FriendRequestViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            imgAvatar = view.findViewById(R.id.imgAvatar);;
            btnAccept = view.findViewById(R.id.btnAccept);;
            btnDecline = view.findViewById(R.id.btnDecline);
        }
    }
}