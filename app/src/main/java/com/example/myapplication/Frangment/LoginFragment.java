package com.example.myapplication.Frangment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.models.sharesimple.AuthViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private AuthViewModel viewModel;
    private FirebaseAuth mAuth;

    private FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        binding.btnDangnhap.setOnClickListener(v -> {
            String email = binding.userEmail.getEditText().getText().toString().trim();
            String pass = binding.userPass.getEditText().getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.signInWithEmail(email, pass, requireActivity(),mAuth);
            }
        });
        binding.btnDangky.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Chuyển sang đăng kí", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_loginFragment2_to_singUpFragment);
        });

        viewModel.getIsSignin().observe(getViewLifecycleOwner(), isSignedIn -> {
            if (isSignedIn) {
                Bundle luu = new Bundle();
                luu.putString("email",binding.userEmail.getEditText().getText().toString().trim());
                Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment2_to_userDetail,luu);
            }
        });

        return binding.getRoot();
    }



}
