package com.group18.dormitory.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobHiring;
import com.group18.dormitory.Model.JobInformation;
import com.group18.dormitory.Model.JobRegistrationInformation;
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
        view = LayoutInflater.from(context).inflate(R.layout.custom_job_register_list_view, parent, false);
        return new JobRegistrationAdapter.JobRegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobRegistrationAdapter.JobRegistrationViewHolder holder, int position) {
        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", items.get(position).getEmployeeId(), UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInfo = (UserInformation) list.get(0);
                holder.txtName.setText(userInfo.getFullName());
                holder.txtGender.setText(userInfo.getGender());
                Glide.with(view).load(userInfo.getAvatar()).error(R.drawable.ic_person).into(holder.imgAvatar);

                DAOs.getInstance().retrieveDataFromDatabase("JobHiring", items.get(holder.getAdapterPosition()).getJobId(), JobHiring.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        JobHiring jobHiring = (JobHiring) list.get(0);
                        holder.txtStoreName.setText(jobHiring.getStoreName());
                        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseFirestore.getInstance().collection("JobInformation")
                                        .whereEqualTo("jobId", items.get(holder.getAdapterPosition()).getJobId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    String jobId = task.getResult().getDocuments().get(0).getId();
                                                    JobInformation jobInfo = task.getResult().getDocuments().get(0).toObject(JobInformation.class);
                                                    jobInfo.getEmployeeId().add(items.get(holder.getAdapterPosition()).getEmployeeId());
                                                    DAOs.getInstance().addDataToDatabase("JobInformation", jobId, jobInfo, new DAOs.OnResultListener() {
                                                        @Override
                                                        public void onResult(boolean result) {
                                                            DAOs.getInstance().deleteDocument("JobRegistration", items.get(holder.getAdapterPosition()).getId());
                                                            items.remove(holder.getAdapterPosition());
                                                            notifyItemRemoved(holder.getAdapterPosition());
                                                            notifyItemRangeChanged(holder.getAdapterPosition(),
                                                                    items.size() - holder.getAdapterPosition());
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        });

                        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DAOs.getInstance().deleteDocument("JobRegistration", items.get(holder.getAdapterPosition()).getId());
                                items.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),
                                        items.size() - holder.getAdapterPosition());
                            }
                        });
                    }
                });

            }
        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(items.get(holder.getAdapterPosition()).getEmployeeId());
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

    public static class JobRegistrationViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtGender;
        TextView txtStoreName;
        ImageView imgAvatar;
        View container;

        ImageButton btnAccept;
        ImageButton btnDecline;

        public JobRegistrationViewHolder(@NonNull View view) {
            super(view);
            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            txtGender = view.findViewById(R.id.txtGender);
            txtStoreName = view.findViewById(R.id.txtStoreName);
            imgAvatar = view.findViewById(R.id.imgAvatar);
            btnAccept = view.findViewById(R.id.btnAccept);
            btnDecline = view.findViewById(R.id.btnDecline);

        }
    }
}