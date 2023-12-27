package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RegisterInformationFragment extends Fragment {
    private View btnCallBack;
    private TextView txtName;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtCitizenId;
    private TextView txtPhoneNumber;
    private TextView txtEmail;
    private TextView txtAddress;

    public RegisterInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_information, container, false);
        initiate(view);




        return view;
    }

    private void initiate(View view) {
        btnCallBack = view.findViewById(R.id.btnCallBack);
        txtName = view.findViewById(R.id.txtName);
        txtBirthday = view.findViewById(R.id.txtBirthday);
        txtGender = view.findViewById(R.id.txtGender);
        txtCitizenId = view.findViewById(R.id.txtCitizenId);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);


        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}