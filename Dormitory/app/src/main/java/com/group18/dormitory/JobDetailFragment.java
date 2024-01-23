package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobHiring;
import com.group18.dormitory.Model.JobRegistrationInformation;

import java.util.List;

public class JobDetailFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;
    private TextView txtStoreName;
    private TextView txtJobPosition;
    private TextView txtWorkPosition;
    private TextView txtSalary;
    private TextView txtContent;
    private Button btnSend;

    public JobDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        txtStoreName = view.findViewById(R.id.txtStoreName);
        txtJobPosition = view.findViewById(R.id.txtJobPosition);
        txtWorkPosition = view.findViewById(R.id.txtWorkPosition);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtContent = view.findViewById(R.id.txtContent);
        btnSend = view.findViewById(R.id.btnSend);
        container = view.findViewById(R.id.container);

        String id = getArguments().getString("JobHiring");
        String currentUserId = DAOs.getInstance().getCurrentUserId();
        CustomProgressBar.getInstance().show(requireContext());
        container.setVisibility(View.GONE);
        DAOs.getInstance().retrieveDataFromDatabase("JobHiring", id, JobHiring.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                System.out.println(id);
                JobHiring jobHiring = (JobHiring) list.get(0);
                txtStoreName.setText(jobHiring.getStoreName());
                txtJobPosition.setText(jobHiring.getJobPosition());
                txtWorkPosition.setText(jobHiring.getStorePosition());
                txtSalary.setText(jobHiring.getSalary());
                txtContent.setText(jobHiring.getContent());

                FirebaseFirestore.getInstance().collection("UserRoles").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String userRole = task.getResult().get("role").toString();
                            switch (userRole) {
                                case "admin": {
                                    btnSend.setVisibility(View.GONE);
                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                    container.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case "student": {

                                    FirebaseFirestore.getInstance().collection("JobRegistration")
                                            .whereEqualTo("jobId", id)
                                            .whereEqualTo("employeeId", currentUserId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.getResult().size() != 0) {
                                                        btnSend.setVisibility(View.GONE);
                                                        CustomProgressBar.getInstance().getDialog().dismiss();
                                                        container.setVisibility(View.VISIBLE);
                                                    } else {
                                                        FirebaseFirestore.getInstance().collection("JobInformation")
                                                                .whereEqualTo("jobId", id)
                                                                .whereArrayContains("employeeId", currentUserId)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.getResult().size() != 0) {
                                                                            btnSend.setVisibility(View.GONE);
                                                                        }
                                                                        CustomProgressBar.getInstance().getDialog().dismiss();
                                                                        container.setVisibility(View.VISIBLE);
                                                                    }
                                                                });
                                                    }
                                                }
                                            });

                                    break;
                                }
                            }
                        }
                    }
                });
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobRegistrationInformation jri = new JobRegistrationInformation(id, currentUserId);
                DAOs.getInstance().addDataToDatabase("JobRegistration", jri.getId(), jri);
                btnSend.setVisibility(View.GONE);
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