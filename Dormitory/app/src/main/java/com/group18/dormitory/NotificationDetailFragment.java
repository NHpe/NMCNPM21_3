package com.group18.dormitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.Notification;

import java.util.List;

public class NotificationDetailFragment extends Fragment {
    private ImageButton btnCallBack;
    private View container;

    private TextView txtTitle;
    private TextView txtContent;
    private TextView txtDate;

    public NotificationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_detail, container, false);
        initiate(view);
        return view;
    }

    private void initiate(View view) {
        container = view.findViewById(R.id.container);
        btnCallBack = view.findViewById(R.id.btnCallBack);
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        txtTitle = view.findViewById(R.id.txtTitle);
        txtContent = view.findViewById(R.id.txtContent);
        txtDate = view.findViewById(R.id.txtDate);

        String notificationId = getArguments().getString("NotificationId");

        container.setVisibility(View.GONE);
        CustomProgressBar.getInstance().show(requireContext());
        DAOs.getInstance().retrieveDataFromDatabase("Notification", notificationId, Notification.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                Notification notification = (Notification) list.get(0);
                txtTitle.setText(notification.getTitle());
                txtContent.setText(notification.getContent());
                txtDate.setText(notification.getDate().toString());
                container.setVisibility(View.VISIBLE);
                CustomProgressBar.getInstance().getDialog().dismiss();
            }
        });


    }
}