package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobRegistrationInformation;
import com.group18.dormitory.Model.Job;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.List;

public class JobRegistrationAdapter extends RecyclerView.Adapter<JobRegistrationAdapter.JobRegistrationViewHolder> {

    private Context context;
    private ArrayList<JobRegistrationInformation> items;
    private View view;
    private JobRegistrationAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(JobRegistrationAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public JobRegistrationAdapter(Context context, ArrayList<JobRegistrationInformation> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public JobRegistrationAdapter.JobRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_room_registration_recycler_view, parent, false);
        return new JobRegistrationAdapter.JobRegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobRegistrationAdapter.JobRegistrationViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public static class JobRegistrationViewHolder extends RecyclerView.ViewHolder{

        TextView txtStudentName;
        TextView txtStudentGender;
        TextView txtRoomName;
        TextView txtRoomGender;
        View container;

        private ImageButton btnAccept;
        private ImageButton btnDecline;

        public JobRegistrationViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtStudentName = view.findViewById(R.id.txtStudentName);
            txtStudentGender = view.findViewById(R.id.txtStudentGender);
            txtRoomName = view.findViewById(R.id.txtRoomName);
            txtRoomGender = view.findViewById(R.id.txtRoomGender);
            container = view.findViewById(R.id.container);
            btnAccept = view.findViewById(R.id.btnAccept);
            btnDecline = view.findViewById(R.id.btnDecline);

        }
    }
}