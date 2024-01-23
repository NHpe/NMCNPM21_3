package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.JobHiring;
import com.group18.dormitory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private Context context;
    private ArrayList<JobHiring> items;
    private View view;
    private JobAdapter.OnItemClickListener onItemClickListener;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
    public void setOnItemClickListener(JobAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public JobAdapter(Context context, ArrayList<JobHiring> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<JobHiring> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_job_list_recycler_view, parent, false);
        return new JobAdapter.JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.JobViewHolder holder, int position) {
        holder.txtName.setText(items.get(position).getStoreName());
        holder.txtJobPosition.setText((items.get(position).getJobPosition()));
        holder.txtStorePosition.setText(items.get(position).getStorePosition());
        holder.txtDate.setText(sdf.format(items.get(position).getDatePost()));
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

        TextView txtJobPosition;
        TextView txtName;
        TextView txtStorePosition;
        TextView txtDate;
        View container;

        public JobViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            txtJobPosition = view.findViewById(R.id.txtJobPosition);
            txtStorePosition = view.findViewById(R.id.txtStorePosition);
            txtDate = view.findViewById(R.id.txtDate);

        }
    }
}