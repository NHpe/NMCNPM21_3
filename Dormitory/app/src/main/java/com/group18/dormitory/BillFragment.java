package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.Bill;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Room;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BillFragment extends Fragment {
    private View btnCallBack;
    private View container;

    private TextView txtRoomName;
    private TextView txtDateStart;
    private TextView txtRoomCost;
    private TextView txtElectricCost;
    private TextView txtWaterCost;
    private TextView txtServiceCost;
    private TextView txtSummary;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        initiate(view);

        return view;
    }

    private void initiate(View view) {
        btnCallBack = view.findViewById(R.id.btnCallBack);

        txtRoomName = view.findViewById(R.id.txtRoomName);
        txtDateStart = view.findViewById(R.id.txtDateStart);
        txtRoomCost = view.findViewById(R.id.txtRoomCost);
        txtElectricCost = view.findViewById(R.id.txtElectricCost);
        txtWaterCost = view.findViewById(R.id.txtWaterCost);
        txtServiceCost = view.findViewById(R.id.txtServiceCost);
        txtSummary = view.findViewById(R.id.txtSummary);
        container = view.findViewById(R.id.container);

        CustomProgressBar.getInstance().show(requireContext());
        container.setVisibility(View.GONE);
        String userId = DAOs.getInstance().getCurrentUserId();

        FirebaseFirestore.getInstance().collection("Bill")
                        .whereEqualTo("studentId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if(task.getResult().size() != 0) {
                                Bill bill = task.getResult().getDocuments().get(0).toObject(Bill.class);
                                long roomCost = getArguments().getLong("RoomCost");
                                txtDateStart.setText(sdf.format(bill.getDateStart()));
                                txtElectricCost.setText("0");
                                txtWaterCost.setText("0");
                                txtServiceCost.setText("0");
                                txtRoomName.setText(getArguments().getString("RoomName"));
                                txtRoomCost.setText(String.valueOf(roomCost));
                                long diff = bill.getDateStart().getTime() - new Date().getTime();
                                long numberOfDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                long totalCost =Long.parseLong(txtElectricCost.getText().toString()) +
                                        Long.parseLong(txtWaterCost.getText().toString()) +
                                        Long.parseLong(txtServiceCost.getText().toString()) +
                                        (long) (roomCost * (float)numberOfDay / 30);
                                txtSummary.setText(String.valueOf(totalCost));

                            }
                        }
                        CustomProgressBar.getInstance().getDialog().dismiss();
                        container.setVisibility(View.VISIBLE);
                    }
                });

        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }
}