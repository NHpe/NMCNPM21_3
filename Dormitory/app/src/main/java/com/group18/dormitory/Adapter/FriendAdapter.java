package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.FriendFragment;
import com.group18.dormitory.Model.Friend;
import com.group18.dormitory.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder>{

    private Context context;
    private ArrayList<Friend> items;
    private View view;
    private FriendAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(FriendAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FriendAdapter(Context context, ArrayList<Friend> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        View container;

        public FriendViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
        }
    }
}
