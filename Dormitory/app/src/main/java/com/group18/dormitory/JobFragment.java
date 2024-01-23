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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.JobAdapter;
import com.group18.dormitory.Adapter.NotificationAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobHiring;

import java.util.ArrayList;
import java.util.List;

public class JobFragment extends Fragment {
    private ImageButton btnCallBack;
    private RecyclerView recyclerView;
    private TextView btnMyJob;
    private TextView btnNewJob;
    private TextView btnRegistration;
    private View container;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        btnMyJob = view.findViewById(R.id.btnMyJob);
        btnNewJob = view.findViewById(R.id.btnNewJob);
        btnRegistration = view.findViewById(R.id.btnRegistration);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        DAOs.getInstance().retrieveDataFromDatabase("JobHiring", JobHiring.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<JobHiring> items = (ArrayList<JobHiring>) list;
                if (items == null) {
                    items = new ArrayList<>();
                }
                JobAdapter adapter = new JobAdapter(requireContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("JobHiring", id);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_jobFragment_to_jobDetailFragment, bundle);
                    }
                });

                String userId = DAOs.getInstance().getCurrentUserId();
                FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String userRole = task.getResult().get("role").toString();
                            switch (userRole) {
                                case "admin": {
                                    btnRegistration.setVisibility(View.VISIBLE);
                                    btnNewJob.setVisibility(View.VISIBLE);
                                    btnMyJob.setVisibility(View.GONE);
                                    break;
                                }
                                case "employee": {
                                    recyclerView.setVisibility(View.GONE);
                                    btnRegistration.setVisibility(View.GONE);
                                    btnNewJob.setVisibility(View.GONE);
                                    btnMyJob.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case "student": {
                                    btnRegistration.setVisibility(View.GONE);
                                    btnNewJob.setVisibility(View.GONE);
                                    btnMyJob.setVisibility(View.VISIBLE);
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

        btnMyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_jobFragment_to_jobScheduleFragment);
            }
        });



        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_jobFragment_to_jobRegistrationFragment);

            }
        });



        btnNewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_jobFragment_to_jobNewFragment);
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