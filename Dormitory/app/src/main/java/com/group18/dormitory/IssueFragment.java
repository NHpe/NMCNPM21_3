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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.IssueAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueFragment extends Fragment {

    private ImageButton btnCallBack;
    private RecyclerView recyclerView;
    private View container;

    public IssueFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        DAOs.getInstance().getNotificationFromDb("Issue", Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {

                ArrayList<Issue> items = (ArrayList<Issue>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                IssueAdapter adapter = new IssueAdapter(requireContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new IssueAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("IssueID", id);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_notificationFragment_to_notificationDetailFragment, bundle);
                    }
                });

                String userId = DAOs.getInstance().getCurrentUserId();
                FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            container.setVisibility(View.VISIBLE);
                        }
                    }
                });
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