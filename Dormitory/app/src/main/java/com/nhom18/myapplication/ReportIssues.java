package com.nhom18.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportIssues#} factory method to
 * create an instance of this fragment.
 */
public class ReportIssues extends Fragment {
    // TODO: Rename and change types of parameters
    private EditText edtFullName;
    private EditText edtID;
    private EditText edtWhat;
    private EditText edtHowLong;
    private EditText edtWhere;
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

        edtFullName = view.findViewById(R.id.edtFullName);
        edtID = view.findViewById(R.id.edtID);
        edtWhat = view.findViewById(R.id.edtWhat);
        edtHowLong = view.findViewById(R.id.edtHowLong);
        edtWhere = view.findViewById(R.id.edtWhere);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtFullName.getText().toString().trim();
                String id = edtID.getText().toString().trim();
                String what = edtWhat.getText().toString().trim();
                String howLong = edtHowLong.getText().toString().trim();
                String where = edtWhere.getText().toString().trim();

                if (name.equals("") || id.equals("") || what.equals("") || howLong.equals("") || where.equals("")) {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    //ToDo
                }
            }
        });
        return view;
    }
}