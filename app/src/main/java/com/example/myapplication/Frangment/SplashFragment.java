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
import com.example.myapplication.Utils;
import com.example.myapplication.databinding.FragmentSplashBinding;
import com.example.myapplication.models.sharesimple.AuthViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                checkUserDetail();
            } else {
                navigateToLogin();
            }
        }, 2000);

        return binding.getRoot();
    }

    private void checkUserDetail() {
        String userID = Utils.INSTANCE.getUserID();
        if (userID.isEmpty()) {
            navigateToLogin();
            return;
        }

        FirebaseDatabase.getInstance().getReference("AllUsers")
                .child("Users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.child("userName").exists()) {
                            // User has completed details, go to MainActivity
                            navigateToMain();
                        } else {
                            // User is logged in but hasn't set details, go to UserDetail
                            // We go to LoginFragment first, and it will handle the flow or 
                            // you can navigate directly to UserDetail if you have the email
                            navigateToLogin(); 
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        navigateToLogin();
                    }
                });
    }

    private void navigateToMain() {
        try {
            Intent intent = new Intent(requireContext(), Class.forName("com.example.myapplication.Activity.MainActivity"));
            startActivity(intent);
            requireActivity().finish();
        } catch (ClassNotFoundException e) {
            navigateToLogin();
        }
    }

    private void navigateToLogin() {
        if (isAdded()) {
            NavHostFragment.findNavController(SplashFragment.this)
                    .navigate(R.id.action_splashFragment_to_loginFragment2);
        }
    }
}
