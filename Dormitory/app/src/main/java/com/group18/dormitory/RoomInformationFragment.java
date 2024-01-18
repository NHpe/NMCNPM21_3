package com.group18.dormitory;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.RoomRegistrationInformation;

import java.util.List;

public class RoomInformationFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;

    private TextView txtName;
    private TextView txtGender;
    private TextView txtMaxNumber;
    private TextView txtCurrentNumber;
    private TextView txtFurniture;
    private TextView txtCost;

    private Button btnRegistration;



    public RoomInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_information, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        txtName = view.findViewById(R.id.txtName);
        txtGender = view.findViewById(R.id.txtGender);
        txtMaxNumber = view.findViewById(R.id.txtMaxNumber);
        txtCurrentNumber = view.findViewById(R.id.txtCurrentNumber);
        txtFurniture = view.findViewById(R.id.txtFurniture);
        txtCost = view.findViewById(R.id.txtCost);
        btnRegistration = view.findViewById(R.id.btnRegistration);
        container = view.findViewById(R.id.container);


        String roomId = getArguments().getString("RoomId");
        String userId = DAOs.getInstance().getCurrentUserId();
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        DAOs.getInstance().retrieveDataFromDatabase("Room", roomId, Room.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                Room room = (Room) list.get(0);
                txtName.setText(txtName.getText() + " " + room.getName());
                txtGender.setText(txtGender.getText() + " " + room.getGender());
                txtMaxNumber.setText(txtMaxNumber.getText() + " " + room.getMaxNumber());
                txtCurrentNumber.setText(txtCurrentNumber.getText() + " " + room.getStudentId().size());
                txtFurniture.setText(txtFurniture.getText() + " " + (room.isFurniture()?"Có":"Không"));
                txtCost.setText(txtCost.getText() + " " + room.getCost());


                FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String userRole = task.getResult().get("role").toString();
                            switch (userRole) {
                                case "admin": {
                                    btnRegistration.setVisibility(View.GONE);
                                    break;
                                }
                            }
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            container.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressBar.getInstance().show(requireContext());
                RoomRegistrationInformation rri = new RoomRegistrationInformation(roomId, userId);
                DAOs.getInstance().addObjectToFirestore("RoomRegistrationInformation", rri, new DAOs.OnResultListener() {
                    @Override
                    public void onResult(boolean result) {
                        CustomProgressBar.getInstance().getDialog().dismiss();
                        new AlertDialog.Builder(requireContext())
                                .setMessage("Gửi đăng ký thành công!")
                                .setNegativeButton("Đóng", null)
                                .setCancelable(false)
                                .show();
                    }
                });
            }
        });

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

}