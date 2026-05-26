package com.example.myapplication.Frangment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Utils;
import com.example.myapplication.databinding.FragmentUserDetailBinding;
import com.example.myapplication.models.sharesimple.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetail extends Fragment {
    private FragmentUserDetailBinding binding;
    public String username = "";
    public String userMail = "";
    public String userID = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        
        getDetail();
        
        return binding.getRoot();
    }

    private void getDetail() {
        userID = Utils.INSTANCE.getUserID();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String email = bundle.getString("email");
            if (email != null) {
                userMail = email;
            }
        }
        binding.btnDuyet.setOnClickListener(v -> {
            if (binding.userIdname.getEditText() != null && binding.userPhone.getEditText() != null) {
                username = binding.userIdname.getEditText().getText().toString().trim();
                String phone = binding.userPhone.getEditText().getText().toString().trim();

                if (username.isEmpty()) {
                    binding.userIdname.setError("Tên không được để trống");
                    return;
                }
                if (phone.isEmpty()) {
                    binding.userPhone.setError("Số điện thoại không được để trống");
                    return;
                }

                Users userObj = new Users();
                userObj.setUid(userID);
                userObj.setUserName(username);
                userObj.setEmail(userMail);
                userObj.setPhoneNumber(phone);

                FirebaseDatabase.getInstance().getReference("AllUsers")
                        .child("Users").child(userID).setValue(userObj)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            try {
                                // Chỉ dùng một Intent đúng tới MainActivity nằm trong package Activity
                                Intent intent = new Intent(requireContext(), Class.forName("com.example.myapplication.Activity.MainActivity"));
                                startActivity(intent);
                                requireActivity().finishAffinity(); // Đóng toàn bộ luồng Login/UserDetail
                            } catch (ClassNotFoundException e) {
                                Toast.makeText(requireContext(), "Lưu thành công! Vui lòng tạo MainActivity để tiếp tục.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
