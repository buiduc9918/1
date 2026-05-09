package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.databinding.FragmentSplashBinding;
public class SplashFragment extends Fragment {
private  FragmentSplashBinding binding;
public SplashFragment() {
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, // container id man hinh hien thi
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            NavHostFragment.findNavController(SplashFragment.this)
                    .navigate(R.id.action_splashFragment_to_loginFragment2);
        }, 2500);
        return binding.getRoot();
    }
}