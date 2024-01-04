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
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Adapter.FriendAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
    private EditText searchBar;
    private RecyclerView recyclerView;
    private View container;
    private ImageButton btnFriendRequest;

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
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        container = view.findViewById(R.id.container);
        btnFriendRequest = view.findViewById(R.id.btnFriendRequest);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        String currentUserId = DAOs.getInstance().getCurrentUserId();

        DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentUserId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                FriendAdapter adapter = new FriendAdapter(requireContext(), friendList.getFriendsId());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_friendFragment_to_chatFragment, bundle);
                    }
                });

                if(friendList.getFriendRequestId().size() != 0) {
                    btnFriendRequest.setImageResource(R.drawable.ic_friend_request);
                } else {
                    btnFriendRequest.setImageResource(R.drawable.ic_friend_request_none);
                }

                container.setVisibility(View.VISIBLE);
                CustomProgressBar.getInstance().getDialog().dismiss();
            }
        });

        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_friendFragment_to_friendFindFragment);
                }
            }
        });

        btnFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_friendFragment_to_friendRequestFragment);
            }
        });
    }
}