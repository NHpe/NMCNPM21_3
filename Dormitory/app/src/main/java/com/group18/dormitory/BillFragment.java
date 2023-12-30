package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.Bill;
import com.group18.dormitory.Model.Room;

public class BillFragment extends Fragment {
    private Bill bill;
    private Room room;
    private View btnBack;
    private View container;

    private TextView roomIDText;
    private TextView dateStartText;
    private TextView dateEndText;
    private TextView costView;
    private TextView electricView;
    private TextView waterView;
    private TextView serviceView;
    private TextView summaryText;

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
        btnBack = view.findViewById(R.id.btnCallBack);

        CustomProgressBar.getInstance().show(requireContext());
        container.setVisibility(View.GONE);

        roomIDText = view.findViewById(R.id.roomIDText);
        dateStartText = view.findViewById(R.id.dateStartText);
        dateEndText = view.findViewById(R.id.dateEndText);
        costView = view.findViewById(R.id.costText);
        electricView = view.findViewById(R.id.electricText);
        waterView = view.findViewById(R.id.waterText);
        serviceView = view.findViewById(R.id.serviceText);
        summaryText = view.findViewById(R.id.summaryText);

        roomIDText.setText("ID phòng : " + bill.getRoomID());
        dateStartText.setText("Ngày bắt đầu : " + bill.getDateStart());
        dateEndText.setText("Ngày kết thúc : " + bill.getDateEnd());
        costView.setText("Giá phòng : " + room.getCost());
        electricView.setText("Tiền điện : " + bill.getElectric());
        waterView.setText("Tiền nước : " + bill.getWater());
        serviceView.setText("Chi phí dịch vụ : " + bill.getService());
        float summary = room.getCost() + bill.getService() + bill.getWater() + bill.getElectric();
        summaryText.setText("Tổng chi phí : " + summary);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_billFragment_to_roomInformationFragment);
            }
        });



    }
}