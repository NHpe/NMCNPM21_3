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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.JobHiring;
import com.group18.dormitory.Model.JobInformation;
import com.group18.dormitory.Model.Notification;

public class JobNewFragment extends Fragment {

    private TextInputLayout boxPos;
    private TextInputLayout boxWorkPos;
    private TextInputLayout boxSalary;
    private TextInputLayout boxContent;
    private TextInputLayout boxName;


    private TextInputEditText etPos;
    private TextInputEditText etName;
    private TextInputEditText etWorkPos;
    private TextInputEditText etSalary;
    private TextInputEditText etContent;

    private ImageButton btnCallBack;
    private Button btnSend;

    public JobNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_new, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        etPos = view.findViewById(R.id.etPos);
        etWorkPos = view.findViewById(R.id.etWorkPos);
        etSalary = view.findViewById(R.id.etSalary);
        etContent = view.findViewById(R.id.etContent);
        etName = view.findViewById(R.id.etName);
        btnSend = view.findViewById(R.id.btnSend);
        boxName = view.findViewById(R.id.boxName);
        boxContent = view.findViewById(R.id.boxContent);
        boxPos = view.findViewById(R.id.boxPos);
        boxWorkPos = view.findViewById(R.id.boxWorkPos);
        boxSalary = view.findViewById(R.id.boxSalary);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ownerId = DAOs.getInstance().getCurrentUserId();
                String name = String.valueOf(etName.getText());
                String pos = String.valueOf(etPos.getText());
                String workPos = String.valueOf(etWorkPos.getText());
                String salary = String.valueOf(etSalary.getText());
                String content = String.valueOf(etContent.getText());

                if(!pos.isEmpty() && !content.isEmpty() && !workPos.isEmpty() && !salary.isEmpty() && !name.isEmpty()) {
                    JobHiring newJobHiring = new JobHiring(ownerId, pos, name, workPos, content, salary);
                    JobInformation jobInfo = new JobInformation(newJobHiring.getId());
                    DAOs.getInstance().addDataToDatabase("JobInformation", jobInfo.getId(), jobInfo);
                    CustomProgressBar.getInstance().show(requireContext());
                    DAOs.getInstance().addDataToDatabase("JobHiring", newJobHiring.getId(), newJobHiring, new DAOs.OnResultListener() {
                        @Override
                        public void onResult(boolean result) {
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            new AlertDialog.Builder(requireContext())
                                    .setMessage("Đã đăng thành công!")
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            NavController navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_jobNewFragment_to_jobFragment);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();

                        }
                    });
                }
            }
        });


        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etName.getText().toString().isEmpty()) {
                        boxName.setErrorEnabled(true);
                        boxName.setError("Không được để trống");
                    } else {
                        boxName.setErrorEnabled(false);
                    }
                }
            }
        });

        etPos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etPos.getText().toString().isEmpty()) {
                        boxPos.setErrorEnabled(true);
                        boxPos.setError("Không được để trống");
                    } else {
                        boxPos.setErrorEnabled(false);
                    }
                }
            }
        });

        etSalary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etSalary.getText().toString().isEmpty()) {
                        boxSalary.setErrorEnabled(true);
                        boxSalary.setError("Không được để trống");
                    } else {
                        boxSalary.setErrorEnabled(false);
                    }
                }
            }
        });

        etWorkPos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etWorkPos.getText().toString().isEmpty()) {
                        boxWorkPos.setErrorEnabled(true);
                        boxWorkPos.setError("Không được để trống");
                    } else {
                        boxWorkPos.setErrorEnabled(false);
                    }
                }
            }
        });

        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etContent.getText().toString().isEmpty()) {
                        boxContent.setErrorEnabled(true);
                        boxContent.setError("Không được để trống");
                    } else {
                        boxContent.setErrorEnabled(false);
                    }
                }
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