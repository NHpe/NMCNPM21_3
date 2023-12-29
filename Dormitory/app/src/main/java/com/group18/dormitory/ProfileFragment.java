package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TYPE = "TYPE";
    public static final int PROFILE = 1;
    public static final int PASSWORD = 2;


    private View container;
    private TextView txtName;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtCitizenId;
    private TextView txtPhoneNumber;
    private TextView txtEmail;
    private TextView txtAddress;
    private TextView btnChangeProfile;
    private View viewPassword;

    private Button btnSignOut;


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

        initiate(view);

        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        txtName = view.findViewById(R.id.txtName);
        txtBirthday = view.findViewById(R.id.txtBirthday);
        txtGender = view.findViewById(R.id.txtGender);
        txtCitizenId = view.findViewById(R.id.txtCitizenId);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);
        viewPassword = view.findViewById(R.id.viewPassword);

        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnChangeProfile = view.findViewById(R.id.btnChangeProfile);

        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(TYPE, PASSWORD);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_profileFragment_to_changeProfileInformationFragment, bundle);
            }
        });

        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        String userId = DAOs.getInstance().getCurrentUserId();
        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", userId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation registerInformation = (UserInformation) list.get(0);
                txtName.setText(registerInformation.getFullName());
                txtBirthday.setText(registerInformation.getBirthday());
                txtGender.setText(registerInformation.getGender());
                txtCitizenId.setText(registerInformation.getCitizenId());
                txtPhoneNumber.setText(registerInformation.getPhoneNumber());
                String email = registerInformation.getEmail();
                txtEmail.setText(email.substring(0,3) + "***" + email.substring(email.indexOf("@")));
                txtAddress.setText(registerInformation.getAddress());
                CustomProgressBar.getInstance().getDialog().dismiss();
                container.setVisibility(View.VISIBLE);
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(TYPE, PROFILE);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_profileFragment_to_changeProfileInformationFragment, bundle);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressBar.getInstance().show(requireContext());
                //TODO do something about delete file before signOut
                DAOs.getInstance().signOut();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_profileFragment_to_signInFragment);
                CustomProgressBar.getInstance().getDialog().dismiss();
            }
        });

    }
}