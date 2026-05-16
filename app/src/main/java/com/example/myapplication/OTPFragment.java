package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentOTPBinding;
// bun chua du lieu dau vao xuyen suot SDT voi key la so
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        blinding = FragmentOTPBinding.inflate(inflater, container, false);
        getUserNumber();
        // Inflate the layout for this fragment
        Toast.makeText(requireContext(),"gui OTP....",Toast.LENGTH_SHORT).show();
        sendOTP();
        onLoginButtonClicked();
        onBackPress();
        return blinding.getRoot();
    }

    private void onBackPress() {
        blinding.toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(OTPFragment.this).navigate(R.id.action_OTPFragment2_to_loginFragment2);
                }
        );
    }

    private void onLoginButtonClicked() {
        blinding.btnTieptuc.setOnClickListener(v -> {
            String OTP = blinding.otpNumber.getEditText().getText().toString();
           goiham(OTP);
        });

    }
void goiham(String OTP){
    if(OTP.length()==6){
        Toast.makeText(requireContext(),"Dang kiem tra OTP ",Toast.LENGTH_SHORT).show();
        verifyOTP(OTP);
    }else {
        Toast.makeText(requireContext(),"OTP  loi ",Toast.LENGTH_SHORT).show();
    }
}
    private void verifyOTP(String otp) {
        viewModel.signInWithPhoneAuthCredential(otp,requireActivity());

        viewModel.getisSignin().observe(getViewLifecycleOwner(),V->{
                    if(V){
                        Toast.makeText(requireContext(),"Login Success",Toast.LENGTH_SHORT).show();
                        blinding.toolbar.setVisibility(View.INVISIBLE);
                        Bundle bundle = new Bundle();
                        bundle.putString("so",number);
                        NavHostFragment.findNavController(OTPFragment.this).navigate(R.id.action_OTPFragment2_to_userDetail,bundle);// them bundle de them lien ket voi agrument lay bien
                    } else {
                        Toast.makeText(requireContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                        blinding.toolbar.setVisibility(View.INVISIBLE);
                    }
                }
        );
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
        blinding.toolbar.setVisibility(View.VISIBLE);
        viewModel.sendOTP(number, requireActivity());
        viewModel.getOtpSent().observe(getViewLifecycleOwner(),isSent->{
            if(isSent){
                Toast.makeText(requireContext(),"Otp da gui thanh cong",Toast.LENGTH_SHORT).show();
                blinding.toolbar.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(requireContext(),"Otp da gui khong thanh cong",Toast.LENGTH_SHORT).show();

            }
        });

    }

}