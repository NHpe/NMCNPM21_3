package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Room;
import com.group18.dormitory.R;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context context;
    private ArrayList<Room> items;
    private View view;
    private RoomAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(RoomAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RoomAdapter(Context context, ArrayList<Room> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Room> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_room_recycler_view, parent, false);
        return new RoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtGender.setText(items.get(position).getGender());
        String number = items.get(position).getStudentId().size() + "/" + items.get(position).getMaxNumber();
        holder.txtNumber.setText(number);
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

    public static class RoomViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtGender;
        TextView txtNumber;
        View container;

        public RoomViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            txtGender = view.findViewById(R.id.txtGender);
            txtNumber = view.findViewById(R.id.txtNumber);

        }
    }
}