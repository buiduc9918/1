package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentUserDetailBinding;

public class UserDetail extends Fragment {
    private  FragmentUserDetailBinding binding;
    public String username = "";
    public String  usernumber = "";
    public String userID = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);

        getDetail();


        return  binding.getRoot();
    }

    private void getDetail() {
        binding.btnDuyet.setOnClickListener(v->{
            username = binding.userIdname.getEditText().toString();
        });
        Bundle bundle = getArguments();
        usernumber = bundle.getString("so").toString();
        // nhan bien Number tu otpfrangment qua agruments; luu y key minh luu trong bundle.

        userID = Utils.INSTANCE.getUserID();



}
}