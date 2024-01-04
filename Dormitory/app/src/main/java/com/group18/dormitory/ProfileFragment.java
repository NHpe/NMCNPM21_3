package com.group18.dormitory;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TYPE = "TYPE";
    public static final int PROFILE = 1;
    public static final int PASSWORD = 2;


    private View container;
    private TextView txtName;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtCitizenId;
    private TextView txtPhoneNumber;
    private TextView txtEmail;
    private TextView txtAddress;
    private TextView btnChangeProfile;
    private View viewPassword;
    private ImageView imgAvatar;

    private Button btnSignOut;
    private View view;


    private Uri selectedImageUri;
    private ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null && data.getData() != null) {
                        selectedImageUri = data.getData();
                        updateAvatar();
                    }
                }
            });
    public ProfileFragment() {
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initiate(view);

        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        txtName = view.findViewById(R.id.txtName);
        txtBirthday = view.findViewById(R.id.txtBirthday);
        txtGender = view.findViewById(R.id.txtGender);
        txtCitizenId = view.findViewById(R.id.txtCitizenId);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);
        viewPassword = view.findViewById(R.id.viewPassword);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnChangeProfile = view.findViewById(R.id.btnChangeProfile);

        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(TYPE, PASSWORD);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_profileFragment_to_changeProfileInformationFragment, bundle);
            }
        });

        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        String userId = DAOs.getInstance().getCurrentUserId();
        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", userId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInfo = (UserInformation) list.get(0);
                txtName.setText(userInfo.getFullName());
                txtBirthday.setText(userInfo.getBirthday());
                txtGender.setText(userInfo.getGender());
                txtCitizenId.setText(userInfo.getCitizenId());
                txtPhoneNumber.setText(userInfo.getPhoneNumber());
                String email = userInfo.getEmail();
                txtEmail.setText(email.substring(0,3) + "***" + email.substring(email.indexOf("@")));
                txtAddress.setText(userInfo.getAddress());
                Glide.with(view).load(userInfo.getAvatar())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(imgAvatar);
                CustomProgressBar.getInstance().getDialog().dismiss();
                container.setVisibility(View.VISIBLE);
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(TYPE, PROFILE);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_profileFragment_to_changeProfileInformationFragment, bundle);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressBar.getInstance().show(requireContext());
                //TODO do something about delete file before signOut
                DAOs.getInstance().signOut();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_profileFragment_to_signInFragment);
                CustomProgressBar.getInstance().getDialog().dismiss();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(view);
            }
        });



    }

    private void imageChooser(View view)
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);

    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = requireContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void updateAvatar() {
        System.out.println("\n\nin");
        String userId = DAOs.getInstance().getCurrentUserId();
        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", userId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInfo = (UserInformation) list.get(0);
                userInfo.setAvatar(selectedImageUri.toString());
                Glide.with(view).load(selectedImageUri).error(R.drawable.ic_person).into(imgAvatar);
                DAOs.getInstance().uploadUserAvatar(userInfo.getId(), selectedImageUri, getFileExtension(selectedImageUri), new DAOs.OnResultUploadAvatarListener() {
                    @Override
                    public void onResult(Uri uri) {
                        userInfo.setAvatar(uri.toString());
                        DAOs.getInstance().addDataToDatabase("UserInformation", userId, userInfo);
                    }
                });
            }
        });
    }


}