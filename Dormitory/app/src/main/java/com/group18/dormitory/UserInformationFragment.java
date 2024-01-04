package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class UserInformationFragment extends Fragment {
    private ImageButton btnCallBack;

    private TextView txtName;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtCitizenId;
    private TextView txtPhoneNumber;
    private TextView txtEmail;
    private TextView txtAddress;
    private ImageView imgAvatar;
    private View container;
    private Button btnNegative;
    private Button btnPositive;

    public UserInformationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);
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
        imgAvatar = view.findViewById(R.id.imgAvatar);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);

        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());

        String id = getArguments().getString("ID");
        String currentUserId = DAOs.getInstance().getCurrentUserId();

        DAOs.getInstance().retrieveDataFromDatabase("UserInformation", id, UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation userInfo = (UserInformation) list.get(0);
                Glide.with(view).load(userInfo.getAvatar())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(imgAvatar);
                txtName.setText(userInfo.getFullName());
                txtBirthday.setText(userInfo.getBirthday());
                txtGender.setText(userInfo.getGender());
                txtCitizenId.setText(userInfo.getCitizenId());
                txtPhoneNumber.setText(userInfo.getPhoneNumber());
                String email = userInfo.getEmail();
                txtEmail.setText(email.substring(0,3) + "***" + email.substring(email.indexOf("@")));
                txtAddress.setText(userInfo.getAddress());

                DAOs.getInstance().retrieveDataFromDatabase("FriendList", id, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        FriendList friendList = (FriendList) list.get(0);
                        if(friendList.getFriendsId().contains(currentUserId)) {
                            btnPositive.setText("Xóa bạn bè");
                            btnNegative.setVisibility(View.GONE);
                            btnPositive.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onDeleteFriendClick(id, currentUserId);
                                }
                            });
                            CustomProgressBar.getInstance().getDialog().dismiss();
                            container.setVisibility(View.VISIBLE);
                        } else {
                            if(friendList.getFriendRequestId().contains(currentUserId)) {
                                btnPositive.setText("Đang chờ phản hồi");
                                btnPositive.setEnabled(false);
                                btnNegative.setVisibility(View.GONE);
                                CustomProgressBar.getInstance().getDialog().dismiss();
                                container.setVisibility(View.VISIBLE);
                            } else {
                                DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentUserId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                                    @Override
                                    public <T> void onComplete(List<T> list) {
                                        FriendList currentUserFriendList = (FriendList) list.get(0);
                                        if(currentUserFriendList.getFriendRequestId().contains(id)) {
                                            btnPositive.setVisibility(View.VISIBLE);
                                            btnPositive.setEnabled(true);
                                            btnPositive.setText("Đồng ý");
                                            btnNegative.setVisibility(View.VISIBLE);
                                            btnNegative.setEnabled(true);
                                            btnNegative.setText("Từ chối");
                                            btnPositive.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    onAcceptFriendClick(id, currentUserId);
                                                }
                                            });
                                            btnNegative.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    onDeclineFriendClick(id, currentUserId);
                                                }
                                            });
                                        } else {
                                            btnPositive.setVisibility(View.VISIBLE);
                                            btnPositive.setEnabled(true);
                                            btnPositive.setText("Thêm bạn");
                                            btnNegative.setVisibility(View.GONE);
                                            btnPositive.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    onAddFriendClick(id, currentUserId);
                                                }
                                            });
                                        }
                                        CustomProgressBar.getInstance().getDialog().dismiss();
                                        container.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                    }
                });


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

    private void onAddFriendClick(String targetId, String currentId) {
        DAOs.getInstance().retrieveDataFromDatabase("FriendList", targetId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                friendList.getFriendRequestId().add(currentId);
                DAOs.getInstance().addDataToDatabase("FriendList", targetId, friendList);
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setText("Đang chờ phản hồi");
                btnPositive.setEnabled(false);
                btnNegative.setVisibility(View.GONE);
            }
        });
    }

    private void onDeleteFriendClick(String targetId, String currentId) {
        DAOs.getInstance().retrieveDataFromDatabase("FriendList", targetId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                friendList.getFriendsId().remove(currentId);
                DAOs.getInstance().addDataToDatabase("FriendList", targetId, friendList);
                DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        FriendList friendList = (FriendList) list.get(0);
                        friendList.getFriendsId().remove(targetId);
                        DAOs.getInstance().addDataToDatabase("FriendList", currentId, friendList);
                        btnPositive.setVisibility(View.VISIBLE);
                        btnPositive.setText("Thêm bạn");
                        btnPositive.setEnabled(true);
                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onAddFriendClick(targetId, currentId);
                            }
                        });
                        btnNegative.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void onAcceptFriendClick(String targetId, String currentId) {

        DAOs.getInstance().retrieveDataFromDatabase("FriendList", targetId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                friendList.getFriendsId().add(currentId);
                DAOs.getInstance().addDataToDatabase("FriendList", targetId, friendList);
                DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        FriendList friendList = (FriendList) list.get(0);
                        friendList.getFriendsId().add(targetId);
                        DAOs.getInstance().addDataToDatabase("FriendList", currentId, friendList);
                        btnPositive.setVisibility(View.VISIBLE);
                        btnPositive.setText("Xóa bạn bè");
                        btnPositive.setEnabled(true);
                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onDeleteFriendClick(targetId, currentId);
                            }
                        });
                        btnNegative.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void onDeclineFriendClick(String targetId, String currentId) {
        DAOs.getInstance().retrieveDataFromDatabase("FriendList", currentId, FriendList.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                FriendList friendList = (FriendList) list.get(0);
                friendList.getFriendRequestId().remove(targetId);
                DAOs.getInstance().addDataToDatabase("FriendList", currentId, friendList);
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setText("Thêm bạn");
                btnPositive.setEnabled(true);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddFriendClick(targetId, currentId);
                    }
                });
                btnNegative.setVisibility(View.GONE);
            }
        });
    }
}