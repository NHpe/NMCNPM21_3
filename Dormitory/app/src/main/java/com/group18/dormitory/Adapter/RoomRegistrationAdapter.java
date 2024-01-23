package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group18.dormitory.Model.Bill;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.RoomRegistrationInformation;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.List;

public class RoomRegistrationAdapter extends RecyclerView.Adapter<RoomRegistrationAdapter.RoomRegistrationViewHolder> {

    private Context context;
    private ArrayList<RoomRegistrationInformation> items;
    private View view;
    private RoomRegistrationAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(RoomRegistrationAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RoomRegistrationAdapter(Context context, ArrayList<RoomRegistrationInformation> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RoomRegistrationAdapter.RoomRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_room_registration_recycler_view, parent, false);
        return new RoomRegistrationAdapter.RoomRegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRegistrationAdapter.RoomRegistrationViewHolder holder, int position) {
        String studentId = items.get(position).getStudentId();
        String roomId = items.get(position).getRoomId();

        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", studentId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInformation = (UserInformation) list.get(0);
                DAOs.getInstance().retrieveDataFromDatabase("Room", roomId, Room.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        Room room = (Room) list.get(0);
                        holder.txtStudentName.setText(holder.txtStudentName.getText() + userInformation.getFullName());
                        holder.txtStudentGender.setText(holder.txtStudentGender.getText() + userInformation.getGender());
                        holder.txtRoomName.setText(holder.txtRoomName.getText() + room.getName());
                        holder.txtRoomGender.setText(holder.txtRoomGender.getText() + room.getGender());

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
                        Bill bill = new Bill(roomId, studentId);
                        DAOs.getInstance().addDataToDatabase("Bill", bill.getBillId(), bill);
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