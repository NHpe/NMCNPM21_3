package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.group18.dormitory.Adapter.FriendAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
    private ImageButton btnSearchFriend;
    private RecyclerView recyclerView;
    private View container;

    public FriendFragment() {
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
        View view = inflater.inflate(R.layout.fragment_friend, container, false);

        initiate(view);

        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        btnSearchFriend = view.findViewById(R.id.btnSearchFriend);
        recyclerView = view.findViewById(R.id.recyclerView);
        /*
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

         */

        String userID = DAOs.getInstance().getCurrentUserId();


        DAOs.getInstance().retrieveDataFromDatabase("Friend", userID, Friend.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                ArrayList<Friend> friendsID = (ArrayList<Friend>) list;

                if (friendsID == null) {
                    friendsID = new ArrayList<>();
                }

                FriendAdapter adapter = new FriendAdapter(requireContext(), friendsID);
                recyclerView.setAdapter(adapter);
            }
        });


        btnSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}