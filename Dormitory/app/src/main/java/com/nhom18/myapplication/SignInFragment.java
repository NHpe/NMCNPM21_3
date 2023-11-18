package com.nhom18.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nhom18.myapplication.Adapter.MyDatabaseHelper;

public class SignInFragment extends Fragment {

    private EditText edtUsername;
    private EditText edtPassword;

    private Button btnSignIn;
    private Button btnSignUp;

    private MyDatabaseHelper myDatabaseHelper;

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

        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(username.equals("")||password.equals(""))
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = myDatabaseHelper.checkUsernamePassword(username,password);
                    if(checkCredentials == true){
                        Toast.makeText(requireContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        //TODO go to next fragment
//                        navController.navigate(R.id.);
                    }else{
                        Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                //TODO go to sign up fragment
//                        navController.navigate(R.id.);
            }
        });
        return view;
    }
}