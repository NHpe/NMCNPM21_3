package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Model.DAOs;

public class SignInFragment extends Fragment {


    private TextInputLayout emailBox;
    private TextInputEditText emailText;
    private TextInputLayout passwordBox;
    private TextInputEditText passwordText;

    private Button btnSignIn;
    private Button btnSignUp;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initiateData(view);
        //TODO forgot password, save password

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if(username.equals("")||password.equals("")) {
                    // DO NOTHING
                }
                else{
                    DAOs.getInstance().signInWithEmailAndPassword(username, password, new DAOs.OnResultListener() {
                        @Override
                        public void onResult(boolean result) {
                            if(result) {
                                Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                //TODO do some work with FirebaseUser
                                //TODO change fragment
                                NavController navController = Navigation.findNavController(v);
                                navController.navigate(R.id.action_signInFragment_to_homeFragment);
                            }else{
                                Toast.makeText(requireContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        return view;
    }

    private void initiateData(View view) {
        emailBox = view.findViewById(R.id.emailBox);
        emailText = view.findViewById(R.id.emailText);
        passwordBox = view.findViewById(R.id.passwordBox);
        passwordText = view.findViewById(R.id.passwordText);

        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(passwordText.getText().toString().isEmpty()) {
                        passwordBox.setErrorEnabled(true);
                        passwordBox.setError("Mật khẩu không được trống và phải có ít nhất 6 ký tự");
                    } else {
                        passwordBox.setErrorEnabled(false);
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