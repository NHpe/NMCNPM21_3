package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Friend;

import java.util.List;

public class FriendFragment extends Fragment {
    private ImageButton btnSearchFriend;
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initiate(view);

        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        btnSearchFriend = view.findViewById(R.id.btnSearchFriend);

        String userID = DAOs.getInstance().getCurrentUserId();

        DAOs.getInstance().retrieveDataFromDatabase("Friend", userID, Friend.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                Friend friendList = (Friend) list.get(0);
            }
        });

        btnSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}