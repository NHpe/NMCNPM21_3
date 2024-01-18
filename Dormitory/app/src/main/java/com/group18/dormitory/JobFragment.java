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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.NotificationAdapter;
import com.group18.dormitory.Adapter.JobAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Notification;
import com.group18.dormitory.Model.Job;

import java.util.ArrayList;
import java.util.List;

public class JobFragment extends Fragment {
    private boolean isDropDown;
    private ImageButton btnCallBack;
    private View container;
    private View btnDropDown;
    private View listDropDown;
    private ImageView arrow;
    private View btnNewJob;
    private RecyclerView recyclerView;
    private Button btnFind;
    private View adminRole;
    private View btnRegistration;


    private CheckBox btnAll;
    private RadioGroup genderGroup;
    private RadioButton btnMale;
    private RadioButton btnFemale;
    private RadioGroup numberGroup;
    private RadioButton btnEight;
    private RadioButton btnFour;
    private RadioButton btnTwo;

    private RadioGroup furnitureGroup;
    private RadioButton btnYes;
    private RadioButton btnNo;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);





        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

}