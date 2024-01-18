package com.group18.dormitory;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Issue;
import com.group18.dormitory.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IssueNewFragment extends Fragment {

    private ImageButton btnCallBack;
    private Spinner issueSpinner;
    private ImageButton btnDatePicker;
    private TextView txtDate;
    private TextInputLayout boxContent;
    private TextInputEditText etContent;
    private Button btnSend;
    private View container;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public IssueNewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_new, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        issueSpinner = view.findViewById(R.id.titleSpinner);
        txtDate = view.findViewById(R.id.txtDate);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        btnSend = view.findViewById(R.id.btnSend);
        etContent = view.findViewById(R.id.etContent);
        boxContent = view.findViewById(R.id.boxContent);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.issue_spinner,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = issueSpinner.getSelectedItem().toString();
                String content = String.valueOf(etContent.getText());
                Date date = new java.util.Date();
                try {
                    date = sdf.parse(txtDate.getText().toString());
                } catch (ParseException e) {
                }
                String id = String.valueOf(new java.util.Date());
                String senderId = DAOs.getInstance().getCurrentUserId();

                if(!content.isEmpty()) {
                    boxContent.setErrorEnabled(false);
                    Issue issue = new Issue(id, senderId, title, content, date);

                    CustomProgressBar.getInstance().show(requireContext());
                    DAOs.getInstance().addDataToDatabase("Issue", issue.getId(), issue, new DAOs.OnResultListener() {
                        @Override
                        public void onResult(boolean result) {
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            new AlertDialog.Builder(requireContext())
                                    .setMessage("Đã gửi thành công!")
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            NavController navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_issueNewFragment_to_issueFragment);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    });


                } else {
                    boxContent.setErrorEnabled(true);
                    boxContent.setError("Không được để trống");
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


        txtDate.setText(sdf.format(new java.util.Date()));
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = String.format("%02d", dayOfMonth) + "/" +
                                        String.format("%02d", (monthOfYear + 1)) + "/" +
                                        year;
                                txtDate.setText(date);
                            }
                        },
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
                datePickerDialog.show();
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