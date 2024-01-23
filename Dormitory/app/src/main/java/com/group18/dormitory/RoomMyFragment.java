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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group18.dormitory.Adapter.FindFriendResultAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Room;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class RoomMyFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;

    private TextView txtName;
    private TextView txtFurniture;
    private TextView txtCost;
    private RecyclerView recyclerView;
    private Button btnShowBill;

    public RoomMyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_my, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        txtName = view.findViewById(R.id.txtName);
        txtFurniture = view.findViewById(R.id.txtFurniture);
        txtCost = view.findViewById(R.id.txtCost);
        container = view.findViewById(R.id.container);
        btnShowBill = view.findViewById(R.id.btnShowBill);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        String roomId = getArguments().getString("RoomId");
        String userId = DAOs.getInstance().getCurrentUserId();
        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        DAOs.getInstance().retrieveDataFromDatabase("Room", roomId, Room.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                Room room = (Room) list.get(0);
                txtName.setText(room.getName());
                txtFurniture.setText((room.isFurniture()?"Có":"Không"));
                txtCost.setText(String.valueOf(room.getCost()));
                btnShowBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("RoomName", room.getName());
                        bundle.putLong("RoomCost", room.getCost());
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_roomMyFragment_to_billFragment, bundle);

                    }
                });

                DAOs.getInstance().retrieveDataFromDatabase("UserInformation", UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
                    @Override
                    public <T> void onComplete(List<T> list) {
                        ArrayList<UserInformation> allUserInformations = (ArrayList<UserInformation>) list;
                        ArrayList<UserInformation> userInfoNeeded = new ArrayList<>();
                        for (UserInformation user :
                                allUserInformations) {
                            if(room.getStudentId().contains(user.getId()) && !user.getId().equals(userId)) {
                                userInfoNeeded.add(user);
                            }
                        }
                        FindFriendResultAdapter adapter = new FindFriendResultAdapter(requireContext(), userInfoNeeded);
                        recyclerView.setAdapter(adapter);
                        container.setVisibility(View.VISIBLE);
                        CustomProgressBar.getInstance().getDialog().dismiss();

                        adapter.setOnItemClickListener(new FindFriendResultAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String id) {
                                Bundle bundle = new Bundle();
                                bundle.putString("ID", id);
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.action_roomMyFragment_to_userInformationFragment, bundle);
                            }
                        });
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
}