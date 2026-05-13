package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Sử dụng ViewBinding để inflate layout
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Khai báo sự kiện setOnClickListener cho button "Tiếp"
        binding.btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy text từ EditText bên trong TextInputLayout
                String number = binding.userNumber.getEditText().getText().toString().trim();
                
                // Kiểm tra số điện thoại (Ví dụ: phải đúng 10 số)
                if (number.length() != 10) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đúng số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("so", "+84" + number);
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment2_to_OTPFragment2, bundle);
                }
            }
        });
        return binding.getRoot();
    }
}