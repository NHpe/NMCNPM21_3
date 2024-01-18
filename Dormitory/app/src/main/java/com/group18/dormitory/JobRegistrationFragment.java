package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.group18.dormitory.Adapter.RegistrationAdapter;
import com.group18.dormitory.Adapter.JobRegistrationAdapter;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobRegistrationInformation;

import java.util.ArrayList;
import java.util.List;

public class JobRegistrationFragment extends Fragment {
    private ImageButton btnCallBack;
    private RecyclerView recyclerView;

    public JobRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_registration, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        recyclerView = view.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RegistrationAdapter(requireContext(), new ArrayList<>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DAOs.getInstance().retrieveDataFromDatabase("JobRegistrationInformation", JobRegistrationInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<JobRegistrationInformation> items = (ArrayList<JobRegistrationInformation>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                JobRegistrationAdapter adapter = new JobRegistrationAdapter(requireContext(), items);
                recyclerView.setAdapter(adapter);
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