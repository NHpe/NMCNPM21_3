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

import com.group18.dormitory.Adapter.FriendRequestAdapter;
import com.group18.dormitory.Adapter.RegistrationAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;
    private RecyclerView recyclerView;

    public FriendRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RegistrationAdapter(requireContext(), new ArrayList<>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        String currentId = DAOs.getInstance().getCurrentUserId();
        DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                FriendRequestAdapter adapter = new FriendRequestAdapter(requireContext(), friendList.getFriendRequestId());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new FriendRequestAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_friendRequestFragment_to_userInformationFragment, bundle);
                    }
                });

                CustomProgressBar.getInstance().getDialog().dismiss();
                container.setVisibility(View.VISIBLE);
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