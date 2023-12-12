package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group18.dormitory.Adapter.DAOs;
import com.group18.dormitory.Model.UserInformation;

public class ProfileFragment extends Fragment {

    private UserInformation user;

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView idView = view.findViewById(R.id.idText);
        TextView fullNameView = view.findViewById(R.id.fullNameText);
        TextView birthdayView = view.findViewById(R.id.birthdayText);
        TextView genderView = view.findViewById(R.id.genderText);
        TextView emailView = view.findViewById(R.id.emailText);
        TextView phoneNumberView = view.findViewById(R.id.phoneNumberText);
        TextView citizenIdView = view.findViewById(R.id.citizenIdText);
        TextView addressView = view.findViewById(R.id.addressText);

        user = DAOs.getInstance().getUser();

        idView.setText("ID : " + user.getId());
        fullNameView.setText("Họ và tên : " + user.getFullName());
        birthdayView.setText("Ngày sinh : " + user.getBirthday());
        genderView.setText("Giới tính : " + user.getGender());
        emailView.setText("Email : " + user.getEmail());
        phoneNumberView.setText("Số điện thoại : " + user.getPhoneNumber());
        citizenIdView.setText("CCCD : " + user.getCitizenId());
        addressView.setText("Địa chỉ : " + user.getAddress());

        return view;
    }
}