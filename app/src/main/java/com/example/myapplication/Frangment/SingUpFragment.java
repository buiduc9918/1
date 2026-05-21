package com.example.myapplication.Frangment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.models.sharesimple.AuthViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSingUpBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SingUpFragment extends Fragment {
   private FragmentSingUpBinding binding;
    private AuthViewModel viewModel;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingUpBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        binding.btnDangky.setOnClickListener(v -> {
            String email = binding.userEmaildk.getEditText().getText().toString().trim();
            String pass = binding.userPassdk.getEditText().getText().toString().trim();
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.signUpWithEmail(email, pass, requireActivity(),mAuth);
            }
        });
        binding.btnVeDangnhap.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Chuyển về đăng nhập", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_singUpFragment_to_loginFragment2);
        });
        viewModel.getIsSignin().observe(getViewLifecycleOwner(), isSignedIn -> {
            if (isSignedIn) {
                Toast.makeText(requireContext(), "Đang chuyển hướng đến phần đăng nhập vui lòng đăng nhập lại tài khoản mình vừa đăng kí", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_singUpFragment_to_loginFragment2);
            }
        });
        return binding.getRoot();
    }
}