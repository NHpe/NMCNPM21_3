package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportIssues#} factory method to
 * create an instance of this fragment.
 */
public class ReportIssues extends Fragment {
    // TODO: Rename and change types of parameters
    private TextInputLayout titleBox;
    private TextInputEditText titleText;
    private TextInputLayout descriptionBox;
    private TextInputEditText descriptionText;
    private Button btnSubmit;

    public ReportIssues() {
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
        View view = inflater.inflate(R.layout.fragment_report_issues, container, false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString().trim();
                String description = descriptionText.getText().toString().trim();
                if (title.equals("") || description.equals("")) {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    //ToDo
                }
            }
        });
        return view;
    }

    private void initiateData(View view) {
        titleBox = view.findViewById(R.id.titleBox);
        titleText = view.findViewById(R.id.titleText);
        descriptionBox = view.findViewById(R.id.descriptionBox);
        descriptionText = view.findViewById(R.id.decriptionText);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        titleText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(titleText.getText().toString().isEmpty()) {
                        titleBox.setErrorEnabled(true);
                        titleBox.setError("Không được để trống");
                    } else {
                        titleBox.setErrorEnabled(false);
                    }
                }
            }
        });

        descriptionText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(descriptionText.getText().toString().isEmpty()) {
                        descriptionBox.setErrorEnabled(true);
                        descriptionBox.setError("Không được để trống");
                    } else {
                        descriptionBox.setErrorEnabled(false);
                    }
                }
            }
        });
    }
}