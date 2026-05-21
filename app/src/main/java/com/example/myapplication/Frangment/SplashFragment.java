package com.example.myapplication.Frangment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSplashBinding;
import com.example.myapplication.models.sharesimple.AuthViewModel;

public class SplashFragment extends Fragment {
    private FragmentSplashBinding binding;
    private AuthViewModel viewModel;

    public SplashFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Boolean isLogged = viewModel.getIsCurrentUser().getValue();
            
            if (isLogged != null && isLogged) {
                // Nếu đã đăng nhập, vào thẳng MainActivity
                try {
                    Intent intent = new Intent(requireContext(), Class.forName("com.example.myapplication.Activity.MainActivity"));
                    startActivity(intent);
                    requireActivity().finish();
                } catch (ClassNotFoundException e) {
                    // Nếu chưa có MainActivity, tạm thời về Login để không bị kẹt
                    NavHostFragment.findNavController(SplashFragment.this)
                            .navigate(R.id.action_splashFragment_to_loginFragment2);
                }
            } else {
                // Nếu chưa đăng nhập, chuyển sang màn hình Login
                NavHostFragment.findNavController(SplashFragment.this)
                        .navigate(R.id.action_splashFragment_to_loginFragment2);
            }
        }, 2500);

        return binding.getRoot();
    }
}
