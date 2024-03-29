package com.group18.dormitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group18.dormitory.Data.CustomProgressBar;
import com.group18.dormitory.Data.MailSender;
import com.group18.dormitory.Model.DAOs;
import com.group18.dormitory.Model.FriendList;
import com.group18.dormitory.Model.UserInformation;
import com.group18.dormitory.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder> {
    private ArrayList<String> items;
    private Context context;
    private View view;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public RegistrationAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RegistrationAdapter.RegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_register_list_view,parent,false);
        return new RegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationViewHolder holder, int position) {
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(items.get(holder.getAdapterPosition()));
                }
            }
        });
        DAOs.getInstance().retrieveDataFromDatabase("RegisterInformation", items.get(position), UserInformation.class, new DAOs.OnCompleteRetrieveDataListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                UserInformation registerInformation = (UserInformation) list.get(0);
                holder.txtName.setText(registerInformation.getFullName());
                holder.txtGender.setText(registerInformation.getGender());
                holder.txtEmail.setText(registerInformation.getEmail());
                holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = registerInformation.getEmail();
                        String password = email.substring(0, 5) + registerInformation.getCitizenId().substring(0, 5);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        String subject = "Đăng ký tài khoản ký túc xá nhóm 18";
                                        String text = "Chào " + registerInformation.getFullName() + ",\n" +
                                                "Đơn đăng ký tạo tài khoản ký túc xá của bạn đã được chấp nhận.\n" +
                                                "Vui lòng đăng nhập vào tài khoản bằng email của bạn với mật khẩu: " +
                                                password + "\n" +
                                                "\nTrân trọng ./.\n" +
                                                "Admin";

                                        MailSender.getInstance().send(registerInformation.getEmail(), subject, text);
                                        registerInformation.setId(authResult.getUser().getUid());
                                        DAOs.getInstance().addDataToDatabase("UserInformation", registerInformation.getId(), registerInformation);
                                        Map<String, String> map = new HashMap<>();
                                        map.put("role", "student");
                                        DAOs.getInstance().addDataToDatabase("UserRoles", registerInformation.getId(), map);
                                        DAOs.getInstance().addDataToDatabase("FriendList", registerInformation.getId(), new FriendList(registerInformation.getId()));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String subject = "Đăng ký tài khoản ký túc xá nhóm 18";
                                        String text = "Chào " + registerInformation.getFullName() + ",\n" +
                                                "Đơn đăng ký tạo tài khoản ký túc xá của bạn không được chấp nhận " +
                                                "do tài khoản email của bạn đã tồn tại trên hệ thống, vui lòng " +
                                                "tạo đơn đăng ký với 1 email mới. \n" +
                                                "\nTrân trọng ./.\n" +
                                                "Admin";

                                        MailSender.getInstance().send(registerInformation.getEmail(), subject, text);
                                    }
                                })
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        FirebaseAuth.getInstance().updateCurrentUser(currentUser);
                                    }
                                });

                        DAOs.getInstance().deleteDocument("RegisterInformation", items.get(holder.getAdapterPosition()));
                        items.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),
                                items.size() - holder.getAdapterPosition());
                    }
                });

                holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subject = "Đăng ký tài khoản ký túc xá nhóm 18";
                        String text = "Chào " + registerInformation.getFullName() + ",\n" +
                                "Đơn đăng ký tạo tài khoản ký túc xá của bạn đã không được chấp nhận.\n" +
                                "Trân trọng ./.\n" +
                                "Admin";

                        MailSender.getInstance().send(registerInformation.getEmail(), subject, text);
                        DAOs.getInstance().deleteDocument("RegisterInformation", items.get(holder.getAdapterPosition()));
                        items.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),
                                items.size() - holder.getAdapterPosition());
                    }
                });
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(String registerId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RegistrationViewHolder extends RecyclerView.ViewHolder{

        View container;
        ImageButton btnAccept;
        ImageButton btnDecline;
        TextView txtName;
        TextView txtGender;
        TextView txtEmail;

        public RegistrationViewHolder(@NonNull View view) {
            super(view);

            container = view.findViewById(R.id.container);
            txtName = view.findViewById(R.id.txtName);
            txtGender = view.findViewById(R.id.txtGender);
            txtEmail = view.findViewById(R.id.txtEmail);

            btnAccept = view.findViewById(R.id.btnAccept);
            btnDecline = view.findViewById(R.id.btnDecline);
        }
    }



}
