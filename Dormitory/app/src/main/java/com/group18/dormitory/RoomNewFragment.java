package com.group18.dormitory;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Room;

public class RoomNewFragment extends Fragment {

    private TextInputLayout boxName;

    private TextInputEditText etName;
    private Button btnAdd;
    private ImageButton btnCallBack;

    private RadioGroup genderGroup;
    private RadioButton btnMale;
    private RadioButton btnFemale;
    private RadioGroup numberGroup;
    private RadioButton btnEight;
    private RadioButton btnFour;
    private RadioButton btnTwo;

    private RadioGroup furnitureGroup;
    private RadioButton btnYes;
    private RadioButton btnNo;

    public RoomNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_new, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        boxName = view.findViewById(R.id.boxName);
        etName = view.findViewById(R.id.etName);
        btnAdd = view.findViewById(R.id.btnAdd);

        genderGroup = view.findViewById(R.id.genderGroup);
        btnMale = view.findViewById(R.id.btnMale);
        btnFemale = view.findViewById(R.id.btnFemale);
        numberGroup = view.findViewById(R.id.numberGroup);
        btnEight = view.findViewById(R.id.btnEight);
        btnFour = view.findViewById(R.id.btnFour);
        btnTwo = view.findViewById(R.id.btnTwo);
        furnitureGroup = view.findViewById(R.id.furnitureGroup);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = etName.getText().toString();
                if(!roomName.isEmpty()) {
                    Room newRoom = new Room();
                    newRoom.setId((new java.util.Date()).toString());
                    newRoom.setName(roomName);
                    RadioButton btn = view.findViewById(genderGroup.getCheckedRadioButtonId());

                    if(btn == btnMale) {
                        newRoom.setGender("Nam");
                    } else {
                        newRoom.setGender("Nữ");
                    }

                    btn = view.findViewById(numberGroup.getCheckedRadioButtonId());

                    if(btn == btnEight) {
                        newRoom.setMaxNumber(8);
                    } else {
                        if(btn == btnFour) {
                            newRoom.setMaxNumber(4);
                        } else {
                            newRoom.setMaxNumber(2);
                        }
                    }

                    btn = view.findViewById(furnitureGroup.getCheckedRadioButtonId());

                    if(btn == btnYes) {
                        newRoom.setFurniture(true);
                    } else {
                        newRoom.setFurniture(false);
                    }

                    CustomProgressBar.getInstance().show(requireContext());
                    DAOs.getInstance().addDataToDatabase("Room", newRoom.getId(), newRoom, new DAOs.OnResultListener() {
                        @Override
                        public void onResult(boolean result) {
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            new AlertDialog.Builder(requireContext())
                                    .setMessage("Thêm phòng thành công!")
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            NavController navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_roomNewFragment_to_roomFragment);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    });

                }

            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etName.getText().toString().isEmpty()) {
                        boxName.setErrorEnabled(true);
                        boxName.setError("Tên phòng không được trống");
                    } else {
                        boxName.setErrorEnabled(false);
                    }
                }
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