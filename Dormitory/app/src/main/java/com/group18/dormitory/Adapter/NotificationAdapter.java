package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Notification;
import com.group18.dormitory.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private ArrayList<Notification> items;
    private View view;
    private NotificationAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(NotificationAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public NotificationAdapter(Context context, ArrayList<Notification> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_notification_recycler_view, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.txtContent.setText(items.get(position).getContent());
        holder.txtDate.setText(items.get(position).getDate().toString());
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

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        TextView txtDate;
        View container;

        public NotificationViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtContent = view.findViewById(R.id.txtContent);
            txtDate = view.findViewById(R.id.txtDate);

        }
    }
}
