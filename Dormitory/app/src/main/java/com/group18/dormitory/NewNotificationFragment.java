package com.group18.dormitory;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Notification;

public class NewNotificationFragment extends Fragment {

    private TextInputLayout boxTitle;
    private TextInputLayout boxContent;

    private TextInputEditText etTitle;
    private TextInputEditText etContent;
    private Button btnSend;
    private ImageButton btnCallBack;

    public NewNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_notification, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        etTitle = view.findViewById(R.id.etTitle);
        etContent = view.findViewById(R.id.etContent);
        btnSend = view.findViewById(R.id.btnSend);
        boxContent = view.findViewById(R.id.boxContent);
        boxTitle = view.findViewById(R.id.boxTitle);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = String.valueOf(etTitle.getText());
                String content = String.valueOf(etContent.getText());

                if(!title.isEmpty() && !content.isEmpty()) {
                    Notification newNotification = new Notification(title, content);
                    CustomProgressBar.getInstance().show(requireContext());
                    DAOs.getInstance().addDataToDatabase("Notification", newNotification.getId(), newNotification, new DAOs.OnResultListener() {
                        @Override
                        public void onResult(boolean result) {
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            new AlertDialog.Builder(requireContext())
                                    .setMessage("Thông báo đã được gửi thành công!")
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            NavController navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_newNotificationFragment_to_notificationFragment);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }
            }
        });




        etTitle.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {
                for(int i = s.length()-1; i >= 0; i--){
                    if(s.charAt(i) == '\n'){
                        s.delete(i, i + 1);
                        return;
                    }
                }
            }
        });

        etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(etTitle.getText().toString().isEmpty()) {
                        boxTitle.setErrorEnabled(true);
                        boxTitle.setError("Không được để trống");
                    } else {
                        boxTitle.setErrorEnabled(false);
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