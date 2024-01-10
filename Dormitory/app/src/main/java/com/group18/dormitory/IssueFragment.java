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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

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
    private Spinner issueSpinner;
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
        issueSpinner = view.findViewById(R.id.issueSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.issue_spinner,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(adapter);

        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}