package com.group18.dormitory;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Adapter.IssueAdapter;
import com.group18.dormitory.Adapter.NotificationAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Issue;
import com.group18.dormitory.Model.Notification;
import com.group18.dormitory.Model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IssueFragment extends Fragment {
    private boolean isDropDown;
    private View filter;
    private View btnDropDown;
    private View listDropDown;
    private ImageView arrow;

    private Button btnFind;

    private ImageButton btnCallBack;
    private View btnNewIssue;
    private View container;
    private RecyclerView recyclerView;


    private CheckBox btnAll;
    private RadioGroup positionGroup;
    private RadioButton btnRoom;
    private RadioButton btnApartment;
    private RadioGroup statusGroup;
    private RadioButton btnFalse;


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
        filter = view.findViewById(R.id.filter);
        btnDropDown = view.findViewById(R.id.btnDropDown);
        listDropDown = view.findViewById(R.id.listDropDown);
        arrow = view.findViewById(R.id.arrow);
        btnNewIssue = view.findViewById(R.id.btnNewIssue);
        btnFind = view.findViewById(R.id.btnFind);



        btnAll = view.findViewById(R.id.btnAll);
        positionGroup = view.findViewById(R.id.positionGroup);
        btnRoom = view.findViewById(R.id.btnRoom);
        btnApartment = view.findViewById(R.id.btnApartment);
        statusGroup = view.findViewById(R.id.statusGroup);
        btnFalse = view.findViewById(R.id.btnFalse);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());


        String userId = DAOs.getInstance().getCurrentUserId();
        FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    String userRole = task.getResult().get("role").toString();
                    switch (userRole) {
                        case "student": {
                            btnNewIssue.setVisibility(View.VISIBLE);
                            filter.setVisibility(View.GONE);
                            DAOs.getInstance().getIssueWithSenderId("Issue", userId, Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
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
                                            bundle.putString("IssueId", id);
                                            NavController navController = Navigation.findNavController(view);
                                            navController.navigate(R.id.action_issueFragment_to_issueDetailFragment, bundle);
                                        }
                                    });
                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                    container.setVisibility(View.VISIBLE);
                                }
                            });
                            break;
                        }

                        case "admin": {
                            btnNewIssue.setVisibility(View.GONE);
                            filter.setVisibility(View.VISIBLE);

                            DAOs.getInstance().getIssueFromDB("Issue", Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
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
                                            bundle.putString("IssueId", id);
                                            NavController navController = Navigation.findNavController(view);
                                            navController.navigate(R.id.action_issueFragment_to_issueDetailFragment, bundle);
                                        }
                                    });
                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                    container.setVisibility(View.VISIBLE);

                                    btnFind.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String position;
                                            boolean status;
                                            RadioButton btn = view.findViewById(positionGroup.getCheckedRadioButtonId());

                                            if(btn == btnRoom) {
                                                position = "Phòng ở";
                                            } else {
                                                if(btn == btnApartment) {
                                                    position = "Tòa nhà";
                                                } else {
                                                    position = "Hầm xe";
                                                }
                                            }

                                            btn = view.findViewById(statusGroup.getCheckedRadioButtonId());

                                            if(btn == btnFalse) {
                                                status = false;
                                            } else {
                                                status = true;
                                            }

                                            if(btnAll.isChecked()) {
                                                CustomProgressBar.getInstance().show(requireContext());
                                                DAOs.getInstance().retrieveDataFromDatabase("Issue", Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
                                                    @Override
                                                    public <T> void onComplete(List<T> list) {

                                                        ArrayList<Issue> newItems = (ArrayList<Issue>) list;
                                                        if (newItems == null) {
                                                            newItems = new ArrayList<>();
                                                        }
                                                        adapter.setItems(newItems);
                                                        CustomProgressBar.getInstance().getDialog().dismiss();
                                                    }
                                                });
                                            } else {
                                                CustomProgressBar.getInstance().show(requireContext());
                                                DAOs.getInstance().getIssueWithSpecific("Issue", position, status,
                                                        Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
                                                            @Override
                                                            public <T> void onComplete(List<T> list) {

                                                                ArrayList<Issue> newItems = (ArrayList<Issue>) list;
                                                                if (newItems == null) {
                                                                    newItems = new ArrayList<>();
                                                                }
                                                                adapter.setItems(newItems);
                                                                CustomProgressBar.getInstance().getDialog().dismiss();

                                                            }
                                                        });

                                            }

                                        }
                                    });
                                }
                            });


                            break;
                        }
                    }
                }
            }
        });

        isDropDown = false;
        btnDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDropDown) {
                    listDropDown.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_arrow_left);
                    isDropDown = false;
                } else {
                    listDropDown.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_arrow_drop_down);
                    isDropDown = true;
                }
            }
        });

        btnNewIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_issueFragment_to_issueNewFragment);
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