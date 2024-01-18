package com.group18.dormitory;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Issue;
import com.group18.dormitory.Model.Room;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class IssueDetailFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;

    private TextView txtTitle;
    private TextView txtDate;
    private TextView txtContent;
    private TextView txtName;
    private Button btnDone;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public IssueDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_detail, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDate = view.findViewById(R.id.txtDate);
        txtContent = view.findViewById(R.id.txtContent);
        txtName = view.findViewById(R.id.txtName);
        btnDone = view.findViewById(R.id.btnDone);
        container = view.findViewById(R.id.container);


        String issueId = getArguments().getString("IssueId");
        String userId = DAOs.getInstance().getCurrentUserId();
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        DAOs.getInstance().retrieveDataFromDatabase("Issue", issueId, Issue.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                Issue issue = (Issue) list.get(0);
                txtTitle.setText(issue.getTitle());
                txtDate.setText(sdf.format(issue.getDate()));
                txtContent.setText(issue.getMessage());

                FirebaseFirestore.getInstance().collection("UserInformation")
                                .whereEqualTo("id", issue.getSenderId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                txtName.setText(task.getResult().getDocuments().get(0).get("fullName").toString());
                            }
                        });

                FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String userRole = task.getResult().get("role").toString();
                            switch (userRole) {
                                case "admin": {
                                    if(!issue.getStatus()) {
                                        btnDone.setVisibility(View.VISIBLE);
                                    }
                                    btnDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CustomProgressBar.getInstance().show(requireContext());
                                            issue.setStatus(true);
                                            DAOs.getInstance().addDataToDatabase("Issue", issueId, issue, new DAOs.OnResultListener() {
                                                @Override
                                                public void onResult(boolean result) {
                                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                                    new AlertDialog.Builder(requireContext())
                                                            .setMessage("Đã lưu thông tin!")
                                                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    NavController navController = Navigation.findNavController(v);
                                                                    navController.navigate(R.id.action_issueDetailFragment_to_issueFragment);
                                                                }
                                                            })
                                                            .setCancelable(false)
                                                            .show();
                                                }
                                            });
                                        }
                                    });
                                    break;
                                }
                            }
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