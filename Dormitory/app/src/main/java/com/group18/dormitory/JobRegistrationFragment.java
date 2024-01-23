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
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobRegistrationInformation;

import java.util.ArrayList;
import java.util.List;

public class JobRegistrationFragment extends Fragment {
    private ImageButton btnCallBack;
    private RecyclerView recyclerView;
    private View container;

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
        btnCallBack = view.findViewById(R.id.btnCallBack);
        recyclerView = view.findViewById(R.id.listView);
        container = view.findViewById(R.id.container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RegistrationAdapter(requireContext(), new ArrayList<>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        DAOs.getInstance().retrieveDataFromDatabase("JobRegistration", JobRegistrationInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<JobRegistrationInformation> items = (ArrayList<JobRegistrationInformation>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                JobRegistrationAdapter adapter = new JobRegistrationAdapter(requireContext(), items);
                adapter.setOnItemClickListener(new JobRegistrationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String registerId) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", registerId);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_jobRegistrationFragment_to_userInformationFragment, bundle);
                    }
                });
                recyclerView.setAdapter(adapter);
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