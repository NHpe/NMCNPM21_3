package com.group18.dormitory;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

public class SignUpFragment extends Fragment {

    private TextInputLayout fullNameBox;
    private TextInputLayout birthdayBox;
    private TextInputLayout citizenIdBox;
    private TextInputLayout phoneNumberBox;
    private TextInputLayout emailBox;
    private TextInputLayout addressBox;
    private TextInputEditText fullNameText;
    private TextInputEditText birthdayText;
    private TextInputEditText citizenIdText;
    private TextInputEditText phoneNumberText;
    private TextInputEditText emailText;
    private TextInputEditText addressText;
    private RadioGroup gender;
    private MaterialRadioButton btnMale;
    private MaterialRadioButton btnFemale;
    private MaterialButton btnSignUp;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initiateData(view);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation newUser = new UserInformation();
                newUser.setFullName(fullNameText.getText().toString());
                newUser.setBirthday(birthdayText.getText().toString());
                newUser.setGender(((MaterialRadioButton)view.findViewById(gender.getCheckedRadioButtonId())).getText().toString());
                newUser.setCitizenId(citizenIdText.getText().toString());
                newUser.setPhoneNumber(phoneNumberText.getText().toString());
                newUser.setEmail(emailText.getText().toString());
                newUser.setAddress(addressText.getText().toString());

                if(!newUser.getFullName().isEmpty() && !newUser.getBirthday().isEmpty()
                    && !newUser.getGender().isEmpty() && !newUser.getCitizenId().isEmpty()
                    && !newUser.getPhoneNumber().isEmpty() && !newUser.getEmail().isEmpty()
                    && !newUser.getAddress().isEmpty()) {
                    //Todo Send request to admin

                    new AlertDialog.Builder(requireContext()).setMessage("Đơn đăng ký đã được gửi đến quản lý," +
                                    " xin vui lòng chờ thông báo qua email")
                            .setPositiveButton("Đóng", null)
                            .show();

                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                }
            }
        });



        return view;
    }




    private void initiateData(View view) {

        fullNameBox = view.findViewById(R.id.fullNameBox);
        birthdayBox = view.findViewById(R.id.birthdayBox);
        citizenIdBox = view.findViewById(R.id.citizenIdBox);
        phoneNumberBox = view.findViewById(R.id.phoneNumberBox);
        emailBox = view.findViewById(R.id.emailBox);
        addressBox = view.findViewById(R.id.addressBox);

        fullNameText = view.findViewById(R.id.fullNameText);
        birthdayText = view.findViewById(R.id.birthdayText);
        citizenIdText = view.findViewById(R.id.citizenIdText);
        phoneNumberText = view.findViewById(R.id.phoneNumberText);
        emailText = view.findViewById(R.id.emailText);
        addressText = view.findViewById(R.id.addressText);

        gender = view.findViewById(R.id.gender);
        btnMale = view.findViewById(R.id.radioBtnMale);
        btnFemale = view.findViewById(R.id.radioBtnFemale);

        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnMale.setChecked(true);

        fullNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(fullNameText.getText().toString().isEmpty()) {
                        fullNameBox.setErrorEnabled(true);
                        fullNameBox.setError("Không được để trống");
                    } else {
                        fullNameBox.setErrorEnabled(false);
                    }
                }
            }
        });

        birthdayText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(birthdayText.getText().toString().isEmpty()) {
                        birthdayBox.setErrorEnabled(true);
                        birthdayBox.setError("Không được để trống");
                    } else {
                        birthdayBox.setErrorEnabled(false);
                    }
                }
            }
        });

        citizenIdText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(citizenIdText.getText().toString().isEmpty()) {
                        citizenIdBox.setErrorEnabled(true);
                        citizenIdBox.setError("Không được để trống");
                    } else {
                        citizenIdBox.setErrorEnabled(false);
                    }
                }
            }
        });

        addressText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(addressText.getText().toString().isEmpty()) {
                        addressBox.setErrorEnabled(true);
                        addressBox.setError("Không được để trống");
                    } else {
                        addressBox.setErrorEnabled(false);
                    }
                }
            }
        });

        phoneNumberText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(phoneNumberText.getText().toString().isEmpty()) {
                        phoneNumberBox.setErrorEnabled(true);
                        phoneNumberBox.setError("Không được để trống");
                    } else {
                        phoneNumberBox.setErrorEnabled(false);
                    }
                }
            }
        });

        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(emailText.getText().toString().isEmpty()) {
                        emailBox.setErrorEnabled(true);
                        emailBox.setError("Không được để trống");
                    } else {
                        emailBox.setErrorEnabled(false);
                    }
                }
            }
        });
    }
}