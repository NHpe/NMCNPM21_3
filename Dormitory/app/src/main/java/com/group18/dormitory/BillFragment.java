package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group18.dormitory.Adapter.DAOs;
import com.group18.dormitory.Model.Bill;
import com.group18.dormitory.Model.Room;

import org.w3c.dom.Text;

public class BillFragment extends Fragment {
    private Bill bill = DAOs.getInstance().getBill();
    private Room room = Room.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        TextView roomIDText = view.findViewById(R.id.roomIDText);
        TextView dateStartText = view.findViewById(R.id.dateStartText);
        TextView dateEndText = view.findViewById(R.id.dateEndText);
        TextView costView = view.findViewById(R.id.costText);
        TextView electricView = view.findViewById(R.id.electricText);
        TextView waterView = view.findViewById(R.id.waterText);
        TextView serviceView = view.findViewById(R.id.serviceText);
        TextView summaryText = view.findViewById(R.id.summaryText);

        roomIDText.setText("ID phòng : " + bill.getRoomID());
        dateStartText.setText("Ngày bắt đầu : " + bill.getDateStart());
        dateEndText.setText("Ngày kết thúc : " + bill.getDateEnd());
        costView.setText("Giá phòng : " + room.getCost());
        electricView.setText("Tiền điện : " + bill.getElectric());
        waterView.setText("Tiền nước : " + bill.getWater());
        serviceView.setText("Chi phí dịch vụ : " + bill.getService());
        float summary = room.getCost() + bill.getService() + bill.getWater() + bill.getElectric();
        summaryText.setText("Tổng chi phí : " + summary);

        return view;
    }
}