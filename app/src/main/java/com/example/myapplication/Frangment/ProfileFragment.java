package com.example.myapplication.Frangment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Utils;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.models.sharesimple.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        
        loadUserProfile();
        setupListeners();
        
        return binding.getRoot();
    }

    private void loadUserProfile() {
        String userID = Utils.INSTANCE.getUserID();
        if (userID.isEmpty()) return;

        FirebaseDatabase.getInstance().getReference("AllUsers")
                .child("Users").child(userID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            if (user != null) {
                                binding.txtProfileName.setText(user.getUserName());
                                binding.txtProfileEmail.setText(user.getEmail());
                                // Bạn có thể thêm code load ảnh đại diện ở đây (Glide/Picasso)
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        if (isAdded()) {
                            Toast.makeText(getContext(), "Lỗi tải thông tin: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setupListeners() {
        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            try {
                Intent intent = new Intent(requireContext(), Class.forName("com.example.myapplication.Activity.AuthActivity"));
                startActivity(intent);
                requireActivity().finishAffinity(); // Đóng toàn bộ luồng MainActivity
            } catch (ClassNotFoundException e) {
                Toast.makeText(getContext(), "Lỗi: Không tìm thấy AuthActivity", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Tính năng chỉnh sửa đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }
}
