package com.group18.dormitory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group18.dormitory.Adapter.FindFriendResultAdapter;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class FriendFindFragment extends Fragment {

    private ImageButton btnCallBack;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private View container;

    public FriendFindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_find, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.requestFocus();
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = String.valueOf(searchBar.getText()).toLowerCase();
                    String userId = DAOs.getInstance().getCurrentUserId();
                    container.setVisibility(View.GONE);
                    CustomProgressBar.getInstance().show(requireContext());
                    DAOs.getInstance().retrieveDataFromDatabase("UserInformation", UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
                        @Override
                        public <T> void onComplete(List<T> list) {
                            ArrayList<UserInformation> allUserInformations = (ArrayList<UserInformation>) list;
                            ArrayList<UserInformation> userInfoNeeded = new ArrayList<>();
                            for (UserInformation user :
                                    allUserInformations) {
                                String userName = user.getFullName().toLowerCase();
                                if(userName.contains(text) && !userId.equals(user.getId())) {
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
                                    navController.navigate(R.id.action_friendFindFragment_to_userInformationFragment, bundle);
                                }
                            });
                        }
                    });


                    return true;
                }
                return false;
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