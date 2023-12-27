package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.List;

public class RegisterInformationFragment extends Fragment {
    private View btnCallBack;
    private View container;
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
        container = view.findViewById(R.id.container);
        btnCallBack = view.findViewById(R.id.btnCallBack);
        txtName = view.findViewById(R.id.txtName);
        txtBirthday = view.findViewById(R.id.txtBirthday);
        txtGender = view.findViewById(R.id.txtGender);
        txtCitizenId = view.findViewById(R.id.txtCitizenId);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        String registerId = getArguments().getString("RegisterId");
        DAOs.getInstance().retrieveDataFromDatabase("RegisterInformation", registerId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation registerInformation = (UserInformation) list.get(0);
                txtName.setText(registerInformation.getFullName());
                txtBirthday.setText(registerInformation.getBirthday());
                txtGender.setText(registerInformation.getGender());
                txtCitizenId.setText(registerInformation.getCitizenId());
                txtPhoneNumber.setText(registerInformation.getPhoneNumber());
                txtEmail.setText(registerInformation.getEmail());
                txtAddress.setText(registerInformation.getAddress());
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