package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.group18.dormitory.Adapter.DAOs;
import com.group18.dormitory.Model.Room;

import java.util.Map;

public class RoomInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private MaterialButton btnBill;
    private Room room = Room.getInstance().getRoom();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_information, container, false);

        TextView idView = view.findViewById(R.id.roomIDText);
        TextView typeView = view.findViewById(R.id.typeText);
        TextView costView = view.findViewById(R.id.costText);
        TextView conditionView = view.findViewById(R.id.conditionText);
        //TableLayout listFurnitureView = view.findViewById(R.id.listFurniture);

        idView.setText("ID Phòng : " + room.getId());
        typeView.setText("Loại phòng : " + room.getType());
        costView.setText("Giá phòng : " + room.getCost());
        conditionView.setText("Tình trạng : " + room.getCondition());

        /*
        Map<String, Integer> furnitures = room.getFurniture();
        String[] nameFurniture = furnitures.keySet().toArray(new String[0]);
        String[] numberFurniture = furnitures.values().toArray(new String[0]);

        TableRow newRow;
        TextView text1 = new TextView();
        text1.setText(nameFurniture[0]);
        TextView text2 = new TextView();
        text2.setText(numberFurniture[0]);

         */

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_roomInformationFragment_to_billFragment);
            }
        });

        return view;
    }
}