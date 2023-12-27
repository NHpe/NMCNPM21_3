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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group18.dormitory.Adapter.RegistrationAdapter;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends Fragment {

    private View btnCallBack;
    private RecyclerView listView;

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
        listView = view.findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        listView.setAdapter(new RegistrationAdapter(requireContext(), new ArrayList<>()));
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));



        DAOs.getInstance().getCollection("RegisterInformation", new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<String> items = (ArrayList<String>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                RegistrationAdapter adapter = new RegistrationAdapter(requireContext(), items);
                listView.setAdapter(adapter);
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