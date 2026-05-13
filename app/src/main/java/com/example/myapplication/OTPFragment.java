package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentOTPBinding;

public class OTPFragment extends Fragment {
    public OTPFragment() {
    }
    private FragmentOTPBinding blinding;
    private AuthViewModel viewModel;
    private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        blinding = FragmentOTPBinding.inflate(inflater, container, false);
        getUserNumber();
        // Inflate the layout for this fragment
        Toast.makeText(requireContext(),"gui OTP....",Toast.LENGTH_SHORT).show();
        sendOTP();
        onLoginButtonClicked();
        return blinding.getRoot();
    }

    private void onLoginButtonClicked() {
        blinding.btnTieptuc.setOnClickListener(v -> {
         String OTP = blinding.otpNumber.toString();
         if(OTP.length()!=6){
             Toast.makeText(requireContext(),"Otp khong co san",Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(requireContext(),"Dang kiem tra OTP ",Toast.LENGTH_SHORT).show();
             verifyOTP(OTP);

         }
        });

    }

    private void verifyOTP(String otp) {
    }

    private void getUserNumber() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Gán giá trị vào biến 'number' của lớp để dùng trong hàm sendOTP()
            number = bundle.getString("so");
            if (number != null) {
                blinding.viewNumber.setText(number);
            }
        }
    }
    private void sendOTP() {
        viewModel.sendOTP(number, requireActivity());
        viewModel.getOtpSent().observe(getViewLifecycleOwner(),isSent->{
            if(isSent){
                Toast.makeText(requireContext(),"Otp da gui thanh cong",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(),"Otp da gui khong thanh cong",Toast.LENGTH_SHORT).show();

            }
        });

    }

}