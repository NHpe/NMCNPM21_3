package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Adapter.NotificationAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Data.MailSender;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Notification;

import java.util.ArrayList;
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
    private RecyclerView recyclerView;


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
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        container = view.findViewById(R.id.container);

        CustomProgressBar.getInstance().show(requireContext());
        container.setVisibility(View.GONE);

        String userId = DAOs.getInstance().getCurrentUserId();

        FirebaseFirestore.getInstance().collection("Notification")
                .orderBy("date", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if(!querySnapshot.isEmpty()) {
                                ArrayList<Notification> result = new ArrayList<>();
                                for (DocumentSnapshot documentSnapshot :
                                        querySnapshot) {
                                    result.add(documentSnapshot.toObject(Notification.class));
                                }
                                NotificationAdapter adapter = new NotificationAdapter(requireContext(), result);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String id) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("NotificationId", id);
                                        NavController navController = Navigation.findNavController(view);
                                        navController.navigate(R.id.action_notificationFragment_to_notificationDetailFragment, bundle);
                                    }
                                });
                            }
                        }
                        FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    String userRole = task.getResult().get("role").toString();
                                    switch (userRole) {
                                        case "student": {
                                            btnRegistration.setVisibility(View.GONE);
                                            break;
                                        }
                                        case "employee": {
                                            btnRegistration.setVisibility(View.GONE);
                                            btnRoom.setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                    container.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });


        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_notificationFragment);
            }
        });

        btnJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_jobFragment);
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