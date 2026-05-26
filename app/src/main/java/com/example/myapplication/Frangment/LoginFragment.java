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
                String email = binding.userEmail.getEditText().getText().toString().trim();
                checkUserDetailAndNavigate(email);
            }
        });

        return binding.getRoot();
    }

    private void checkUserDetailAndNavigate(String email) {
        String userID = com.example.myapplication.Utils.INSTANCE.getUserID();
        com.google.firebase.database.FirebaseDatabase.getInstance().getReference("AllUsers")
                .child("Users").child(userID)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.child("userName").exists()) {
                            // User completed details, go to MainActivity
                            try {
                                android.content.Intent intent = new android.content.Intent(requireContext(), Class.forName("com.example.myapplication.Activity.MainActivity"));
                                startActivity(intent);
                                requireActivity().finish();
                            } catch (ClassNotFoundException e) {
                                Toast.makeText(requireContext(), "Lỗi: Không tìm thấy MainActivity", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // First time login or details missing, go to UserDetail
                            Bundle luu = new Bundle();
                            luu.putString("email", email);
                            Toast.makeText(requireContext(), "Vui lòng hoàn tất thông tin", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment2_to_userDetail, luu);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                        Toast.makeText(requireContext(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
