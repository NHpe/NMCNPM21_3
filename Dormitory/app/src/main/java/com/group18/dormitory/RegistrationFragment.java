package com.group18.dormitory;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group18.dormitory.Adapter.RegistrationAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends Fragment {

    private View btnCallBack;
    private View container;
    private RecyclerView recyclerView;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
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

        DAOs.getInstance().getCollection("RegisterInformation", new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<String> items = (ArrayList<String>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                RegistrationAdapter adapter = new RegistrationAdapter(requireContext(), items);
                adapter.setOnItemClickListener(new RegistrationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String registerId) {
                        Bundle bundle = new Bundle();
                        bundle.putString("RegisterId", registerId);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_registrationFragment_to_registerInformationFragment, bundle);
                    }
                });
                recyclerView.setAdapter(adapter);
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