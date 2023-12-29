package com.group18.dormitory;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.UserInformation;

import java.util.List;

public class ProfileChangeInformationFragment extends Fragment {
    private TextView txtChangeType;
    private TextInputLayout firstBox;
    private TextInputLayout secondBox;
    private TextInputLayout thirdBox;
    private TextInputLayout fourthBox;
    private TextInputLayout fifthBox;
    private TextInputLayout sixthBox;
    private TextInputEditText firstBoxText;
    private TextInputEditText secondBoxText;
    private TextInputEditText thirdBoxText;
    private TextInputEditText fourthBoxText;
    private TextInputEditText fifthBoxText;
    private TextInputEditText sixthBoxText;
    private ImageButton btnCallBack;

    private Button btnSaveChange;
    private View container;


    public ProfileChangeInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_change_information, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        txtChangeType = view.findViewById(R.id.txtChangeType);
        container = view.findViewById(R.id.container);

        firstBox = view.findViewById(R.id.firstBox);
        secondBox = view.findViewById(R.id.secondBox);
        thirdBox = view.findViewById(R.id.thirdBox);
        fourthBox = view.findViewById(R.id.fourthBox);
        fifthBox = view.findViewById(R.id.fifthBox);
        sixthBox = view.findViewById(R.id.sixthBox);

        firstBoxText = view.findViewById(R.id.firstBoxText);
        secondBoxText = view.findViewById(R.id.secondBoxText);
        thirdBoxText = view.findViewById(R.id.thirdBoxText);
        fourthBoxText = view.findViewById(R.id.fourthBoxText);
        fifthBoxText = view.findViewById(R.id.fifthBoxText);
        sixthBoxText = view.findViewById(R.id.sixthBoxText);



        btnSaveChange = view.findViewById(R.id.btnSaveChange);
        btnCallBack = view.findViewById(R.id.btnCallBack);

        int changeType = getArguments().getInt(ProfileFragment.TYPE);

        switch (changeType) {
            case ProfileFragment.PROFILE: {
                txtChangeType.setText("Thông tin cá nhân");
                firstBox.setHint("Họ và tên");
                firstBoxText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                secondBox.setHint("Ngày sinh");
                secondBoxText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
//                thirdBox.setHint("Giới tính");
//                thirdBoxText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                thirdBox.setVisibility(View.GONE);
                fourthBox.setHint("Mã căn cước công dân");
                fourthBoxText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                fifthBox.setHint("Số điện thoại");
                fifthBoxText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                sixthBox.setHint("Quê quán");
                sixthBoxText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                container.setVisibility(View.GONE);

                CustomProgressBar.getInstance().show(requireContext());
                String userId = DAOs.getInstance().getCurrentUserId();
                DAOs.getInstance().retrieveDataFromDatabase("UserInformation", userId, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        UserInformation userInformation = (UserInformation) list.get(0);
                        firstBoxText.setText(userInformation.getFullName());
                        secondBoxText.setText(userInformation.getBirthday());
//                        thirdBoxText.setText(userInformation.getGender());
                        fourthBoxText.setText(userInformation.getCitizenId());
                        fifthBoxText.setText(userInformation.getPhoneNumber());
                        sixthBoxText.setText(userInformation.getAddress());
                        CustomProgressBar.getInstance().getDialog().dismiss();
                        container.setVisibility(View.VISIBLE);

                        btnSaveChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userInformation.setFullName(firstBoxText.getText().toString());
                                userInformation.setBirthday(secondBoxText.getText().toString());
                                userInformation.setCitizenId(fourthBoxText.getText().toString());
                                userInformation.setPhoneNumber(fifthBoxText.getText().toString());
                                userInformation.setAddress(sixthBoxText.getText().toString());
                                if(!userInformation.getFullName().isEmpty() && !userInformation.getBirthday().isEmpty()
                                        && !userInformation.getCitizenId().isEmpty() && !userInformation.getPhoneNumber().isEmpty()
                                        && !userInformation.getAddress().isEmpty()) {
                                    CustomProgressBar.getInstance().show(requireContext());
                                    DAOs.getInstance().addDataToDatabase("UserInformation", userId, userInformation, new DAOs.OnResultListener() {
                                        @Override
                                        public void onResult(boolean result) {
                                            CustomProgressBar.getInstance().getDialog().dismiss();
                                            new AlertDialog.Builder(requireContext())
                                                    .setMessage("Thay đổi thông tin thành công")
                                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            NavController navController = Navigation.findNavController(v);
                                                            navController.navigate(R.id.action_changeProfileInformationFragment_to_profileFragment);
                                                        }
                                                    })
                                                    .setCancelable(false)
                                                    .show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });

                break;
            }

            case ProfileFragment.PASSWORD: {
                txtChangeType.setText("Mật khẩu");
                firstBox.setHint("Mật khẩu hiện tại");
                firstBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                secondBox.setHint("Mật khẩu mới");
                secondBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                thirdBox.setHint("Nhập lại mật khẩu mới");
                thirdBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                fourthBox.setVisibility(View.GONE);
                fifthBox.setVisibility(View.GONE);
                sixthBox.setVisibility(View.GONE);

                btnSaveChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPass = firstBoxText.getText().toString();
                        String pass1 = secondBoxText.getText().toString();
                        String pass2 = thirdBoxText.getText().toString();
                        DAOs.getInstance().reAuthenticate(oldPass, new DAOs.OnResultListener() {
                            @Override
                            public void onResult(boolean result) {
                                if(!result) {
                                    firstBox.setErrorEnabled(true);
                                    firstBox.setError("Sai mật khẩu!");
                                }else {
                                    firstBox.setErrorEnabled(false);
                                    if(pass1.equals("") || pass1.length() < 6) {
                                        secondBox.setErrorEnabled(true);
                                        secondBox.setError("Mật khẩu không được trống và phải có hơn 6 ký tự!");
                                    } else {
                                        secondBox.setErrorEnabled(false);
                                        if (pass1.equals(pass2)) {
                                            thirdBox.setErrorEnabled(false);
                                            CustomProgressBar.getInstance().show(requireContext());
                                            DAOs.getInstance().changePassword(secondBoxText.getText().toString(), new DAOs.OnResultListener() {
                                                @Override
                                                public void onResult(boolean result) {
                                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                                    new AlertDialog.Builder(requireContext())
                                                            .setMessage("Thay đổi thông tin thành công")
                                                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    NavController navController = Navigation.findNavController(v);
                                                                    navController.navigate(R.id.action_changeProfileInformationFragment_to_profileFragment);
                                                                }
                                                            })
                                                            .setCancelable(false)
                                                            .show();
                                                }
                                            });
                                        } else {
                                            thirdBox.setErrorEnabled(true);
                                            thirdBox.setError("Mật khẩu không giống nhau!");
                                        }
                                    }
                                }
                            }
                        });
                    }
                });


                break;
            }

        }

        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });



        firstBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(firstBoxText.getText().toString().isEmpty()) {
                        firstBox.setErrorEnabled(true);
                        firstBox.setError("Không được để trống");
                    } else {
                        firstBox.setErrorEnabled(false);
                    }
                }
            }
        });



        secondBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(secondBoxText.getText().toString().isEmpty()) {
                        secondBox.setErrorEnabled(true);
                        secondBox.setError("Không được để trống");
                    } else {
                        secondBox.setErrorEnabled(false);
                    }
                }
            }
        });



        thirdBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(thirdBoxText.getText().toString().isEmpty()) {
                        thirdBox.setErrorEnabled(true);
                        thirdBox.setError("Không được để trống");
                    } else {
                        thirdBox.setErrorEnabled(false);
                    }
                }
            }
        });



        fourthBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(fourthBoxText.getText().toString().isEmpty()) {
                        fourthBox.setErrorEnabled(true);
                        fourthBox.setError("Không được để trống");
                    } else {
                        fourthBox.setErrorEnabled(false);
                    }
                }
            }
        });

        fifthBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(fifthBoxText.getText().toString().isEmpty()) {
                        fifthBox.setErrorEnabled(true);
                        fifthBox.setError("Không được để trống");
                    } else {
                        fifthBox.setErrorEnabled(false);
                    }
                }
            }
        });

        sixthBoxText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(sixthBoxText.getText().toString().isEmpty()) {
                        sixthBox.setErrorEnabled(true);
                        sixthBox.setError("Không được để trống");
                    } else {
                        sixthBox.setErrorEnabled(false);
                    }
                }
            }
        });
    }
}