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
        String employeeId = items.get(position).getEmployeeId();
        String JobId = items.get(position).getJobId();

        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", employeeId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInformation = (UserInformation) list.get(0);
                DAOs.getInstance().retrieveDataFromDatabase("Job", Id, Job.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        Job job = (Job) list.get(0);


                    }
                });

            }
        });

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOs.getInstance().retrieveDataFromDatabase("Room", roomId, Room.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        Room room = (Room) list.get(0);
                        room.getStudentId().add(studentId);
                        DAOs.getInstance().addDataToDatabase("Room", roomId, room);
                    }
                });


                DAOs.getInstance().getRoomRegistrationId(studentId, roomId, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        String id = (String) list.get(0);
                        DAOs.getInstance().deleteDocument("RoomRegistrationInformation", id);
                        items.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),
                                items.size() - holder.getAdapterPosition());
                    }
                });
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOs.getInstance().getRoomRegistrationId(studentId, roomId, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        String id = (String) list.get(0);
                        DAOs.getInstance().deleteDocument("RoomRegistrationInformation", id);
                        items.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),
                                items.size() - holder.getAdapterPosition());
                    }
                });

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

    public static class RoomRegistrationViewHolder extends RecyclerView.ViewHolder{

        TextView txtStudentName;
        TextView txtStudentGender;
        TextView txtRoomName;
        TextView txtRoomGender;
        View container;

        private ImageButton btnAccept;
        private ImageButton btnDecline;

        public RoomRegistrationViewHolder(@NonNull View view) {
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