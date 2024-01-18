package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Issue;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {

    private Context context;
    private ArrayList<Issue> items;
    private View view;
    private IssueAdapter.OnItemClickListener onItemClickListener;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public void setOnItemClickListener(IssueAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public IssueAdapter(Context context, ArrayList<Issue> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Issue> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_issue_list_recycler_view, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueAdapter.IssueViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.txtStatus.setText(!items.get(position).getStatus()?"Đang xử lý":"Đã xử lý");
        holder.txtDate.setText(sdf.format(items.get(position).getDate()));
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
        TextView txtStatus;
        TextView txtDate;
        View container;

        public IssueViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtStatus = view.findViewById(R.id.txtStatus);
            txtDate = view.findViewById(R.id.txtDate);

        }
    }
}
