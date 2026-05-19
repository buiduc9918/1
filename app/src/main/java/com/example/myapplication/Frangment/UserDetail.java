package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentUserDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetail extends Fragment {
    private FragmentUserDetailBinding binding;
    public String username = "";
    public String userMail = "";
    public String userID = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userMail = user.getEmail();
            userID = user.getUid();
        }
        getDetail();
        return binding.getRoot();
    }

    private void getDetail() {
        binding.btnDuyet.setOnClickListener(v -> {
            if (binding.userIdname.getEditText() != null) {
                username = binding.userIdname.getEditText().getText().toString();
            }
        });
    }
}
