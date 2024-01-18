package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Job;
import com.group18.dormitory.R;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private Context context;
    private ArrayList<Job> items;
    private View view;
    private JobAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(JobAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public JobAdapter(Context context, ArrayList<Job> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Job> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_room_recycler_view, parent, false);
        return new JobAdapter.JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.JobViewHolder holder, int position) {
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

    public static class JobViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtGender;
        TextView txtNumber;
        View container;

        public JobViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            txtGender = view.findViewById(R.id.txtGender);
            txtNumber = view.findViewById(R.id.txtNumber);

        }
    }
}