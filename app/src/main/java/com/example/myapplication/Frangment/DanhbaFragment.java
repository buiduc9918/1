package com.example.myapplication.Frangment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDanhbaBinding;

public class DanhbaFragment extends Fragment {
    public FragmentDanhbaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDanhbaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}