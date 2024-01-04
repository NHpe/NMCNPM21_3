package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.List;

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

                holder.name.setText(friendInfo.getFullName());
//        holder.message.setText(items.get(position).getGender());
//        holder.lastTime.setText();
                Glide.with(view).load(friendInfo.getAvatar()).error(R.drawable.ic_person).into(holder.avatar);

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