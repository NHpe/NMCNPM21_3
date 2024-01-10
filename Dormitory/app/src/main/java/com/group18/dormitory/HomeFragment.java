package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Data.MailSender;
import com.group18.dormitory.Model.DAOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private View btnRegistration;
    private View btnNotification;
    private View btnRoom;
    private View btnIssue;
    private View btnJob;
    private View container;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initiate(view);


        return view;
    }

    private void initiate(View view) {
        btnRegistration = view.findViewById(R.id.btnRegistration);
        btnRoom = view.findViewById(R.id.btnRoom);
        btnNotification = view.findViewById(R.id.btnNotification);
        btnJob = view.findViewById(R.id.btnJob);
        btnIssue = view.findViewById(R.id.btnIssue);

        container = view.findViewById(R.id.container);

        CustomProgressBar.getInstance().show(requireContext());
        container.setVisibility(View.GONE);

        String userId = DAOs.getInstance().getCurrentUserId();
        FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    String userRole = task.getResult().get("role").toString();
                    switch (userRole) {
                        case "student": {
                            btnRegistration.setVisibility(View.GONE);
                        }
                        /*
                        case "manager": {
                            btnReportIssue.setVisibility(View.GONE);
                        }
                         */
                    }
                    CustomProgressBar.getInstance().getDialog().dismiss();
                    container.setVisibility(View.VISIBLE);
                }
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_notificationFragment);
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_registrationFragment);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_roomFragment);
            }
        });

        btnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_issueFragment);
            }
        });
    }
}