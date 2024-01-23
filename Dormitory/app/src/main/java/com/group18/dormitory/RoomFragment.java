package com.group18.dormitory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Adapter.NotificationAdapter;
import com.group18.dormitory.Adapter.RoomAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Notification;
import com.group18.dormitory.Model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {
    private boolean isDropDown;
    private ImageButton btnCallBack;
    private View container;
    private View btnDropDown;
    private View listDropDown;
    private ImageView arrow;
    private View btnNewRoom;
    private RecyclerView recyclerView;
    private Button btnFind;
    private View adminRole;
    private View btnRegistration;


    private CheckBox btnAll;
    private RadioGroup genderGroup;
    private RadioButton btnMale;
    private RadioButton btnFemale;
    private RadioGroup numberGroup;
    private RadioButton btnEight;
    private RadioButton btnFour;
    private RadioButton btnTwo;

    private RadioGroup furnitureGroup;
    private RadioButton btnYes;
    private RadioButton btnNo;
    private View btnMyRoom;

    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        btnDropDown = view.findViewById(R.id.btnDropDown);
        listDropDown = view.findViewById(R.id.listDropDown);
        arrow = view.findViewById(R.id.arrow);
        btnNewRoom = view.findViewById(R.id.btnNewRoom);
        btnRegistration = view.findViewById(R.id.btnRegistration);
        isDropDown = false;
        btnFind = view.findViewById(R.id.btnFind);
        adminRole = view.findViewById(R.id.adminRole);
        btnMyRoom = view.findViewById(R.id.btnMyRoom);

        btnAll = view.findViewById(R.id.btnAll);
        genderGroup = view.findViewById(R.id.genderGroup);
        btnMale = view.findViewById(R.id.btnMale);
        btnFemale = view.findViewById(R.id.btnFemale);
        numberGroup = view.findViewById(R.id.numberGroup);
        btnEight = view.findViewById(R.id.btnEight);
        btnFour = view.findViewById(R.id.btnFour);
        btnTwo = view.findViewById(R.id.btnTwo);
        furnitureGroup = view.findViewById(R.id.furnitureGroup);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);

        btnDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDropDown) {
                    listDropDown.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_arrow_left);
                    isDropDown = false;
                } else {
                    listDropDown.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_arrow_drop_down);
                    isDropDown = true;
                }
            }
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        DAOs.getInstance().retrieveDataFromDatabase("Room", Room.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {

                ArrayList<Room> items = (ArrayList<Room>) list;
                if(items == null) {
                    items = new ArrayList<>();
                }
                RoomAdapter adapter = new RoomAdapter(requireContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new RoomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("RoomId", id);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_roomFragment_to_roomInformationFragment, bundle);

                    }
                });

                String userId = DAOs.getInstance().getCurrentUserId();
                FirebaseFirestore.getInstance().collection("UserRoles").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String userRole = task.getResult().get("role").toString();
                            switch (userRole) {
                                case "admin": {
                                    container.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case "student": {
                                    container.setVisibility(View.VISIBLE);
                                    FirebaseFirestore.getInstance().collection("Room")
                                            .whereArrayContains("studentId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        if (task.getResult().size()!=0) {
                                                            btnMyRoom.setVisibility(View.VISIBLE);
                                                            btnMyRoom.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("RoomId", task.getResult()
                                                                            .getDocuments().get(0).toObject(Room.class).getId());
                                                                    NavController navController = Navigation.findNavController(v);
                                                                    navController.navigate(R.id.action_roomFragment_to_roomMyFragment, bundle);
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                            adminRole.setVisibility(View.VISIBLE);
                            CustomProgressBar.getInstance().getDialog().dismiss();
                        }
                    }
                });

                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String gender;
                        int maxNumber;
                        boolean hasFurniture;
                        RadioButton btn = view.findViewById(genderGroup.getCheckedRadioButtonId());

                        if(btn == btnMale) {
                            gender = "Nam";
                        } else {
                            gender = "Nữ";
                        }

                        btn = view.findViewById(numberGroup.getCheckedRadioButtonId());

                        if(btn == btnEight) {
                            maxNumber = 8;
                        } else {
                            if(btn == btnFour) {
                                maxNumber = 4;
                            } else {
                                maxNumber = 2;
                            }
                        }

                        btn = view.findViewById(furnitureGroup.getCheckedRadioButtonId());

                        if(btn == btnYes) {
                            hasFurniture = true;
                        } else {
                            hasFurniture = false;
                        }

                        if(btnAll.isChecked()) {
                            CustomProgressBar.getInstance().show(requireContext());
                            DAOs.getInstance().retrieveDataFromDatabase("Room", Room.class, new DAOs.OnCompleteRetrieveDataListener() {
                                @Override
                                public <T> void onComplete(List<T> list) {

                                    ArrayList<Room> newItems = (ArrayList<Room>) list;
                                    if (newItems == null) {
                                        newItems = new ArrayList<>();
                                    }
                                    adapter.setItems(newItems);
                                    CustomProgressBar.getInstance().getDialog().dismiss();
                                }
                            });
                        } else {
                            CustomProgressBar.getInstance().show(requireContext());
                            DAOs.getInstance().getRoomWithSpecific("Room", gender, maxNumber, hasFurniture,
                                    Room.class, new DAOs.OnCompleteRetrieveDataListener() {
                                        @Override
                                        public <T> void onComplete(List<T> list) {

                                            ArrayList<Room> newItems = (ArrayList<Room>) list;
                                            if (newItems == null) {
                                                newItems = new ArrayList<>();
                                            }
                                            adapter.setItems(newItems);
                                            CustomProgressBar.getInstance().getDialog().dismiss();

                                        }
                                    });

                        }

                    }
                });
            }
        });

        btnNewRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_roomFragment_to_roomNewFragment);
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_roomFragment_to_roomRegistrationFragment);

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