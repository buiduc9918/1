package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
               var number = binding.userNumber.toString();
               if(number.length() == 0||number.length() !=10){

               }
            }
        });

        return binding.getRoot();
    }
//    // Chuyển sang OTPFragment khi nhấn nút trong onClick
//                NavHostFragment.findNavController(LoginFragment.this)
//            .navigate(R.id.action_loginFragment2_to_OTPFragment2);
}