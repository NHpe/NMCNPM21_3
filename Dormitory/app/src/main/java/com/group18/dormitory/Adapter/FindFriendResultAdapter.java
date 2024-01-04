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
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.List;

public class FindFriendResultAdapter extends RecyclerView.Adapter<FindFriendResultAdapter.FindFriendResultViewHolder> {

    private Context context;
    private ArrayList<UserInformation> items;
    private View view;
    private FindFriendResultAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FindFriendResultAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FindFriendResultAdapter(Context context, ArrayList<UserInformation> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<UserInformation> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindFriendResultAdapter.FindFriendResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_friend_find_result_recycler_view, parent, false);
        return new FindFriendResultAdapter.FindFriendResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindFriendResultAdapter.FindFriendResultViewHolder holder, int position) {
        holder.name.setText(items.get(position).getFullName());
        Glide.with(view).load(items.get(position).getAvatar()).error(R.drawable.ic_person).into(holder.avatar);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(items.get(holder.getAdapterPosition()).getId());
                }
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

    public static class FindFriendResultViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;

        TextView name;
        View container;

        public FindFriendResultViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            name = view.findViewById(R.id.name);
            avatar = view.findViewById(R.id.avatar);
        }
    }
}