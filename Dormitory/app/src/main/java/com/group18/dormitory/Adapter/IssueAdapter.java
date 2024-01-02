package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Issue;
import com.group18.dormitory.R;

import java.util.ArrayList;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {

    private Context context;
    private ArrayList<Issue> items;
    private View view;
    private IssueAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(IssueAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public IssueAdapter(Context context, ArrayList<Issue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_notification_recycler_view, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueAdapter.IssueViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.txtContent.setText(items.get(position).getMessage());
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

    public static class IssueViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        TextView txtDate;
        View container;

        public IssueViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtContent = view.findViewById(R.id.txtContent);
            txtDate = view.findViewById(R.id.txtDate);

        }
    }
}
