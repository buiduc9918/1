package com.example.myapplication;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class AuthViewModel extends ViewModel {

    private MutableLiveData<String> _verification = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> _otpSent = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> _isSignin =  new MutableLiveData<>(false);

    public static final AuthViewModel INSTANCE = new AuthViewModel();

    MutableLiveData<Boolean> getisSignin() {
        return _isSignin;
    }

    MutableLiveData<String> getVerificationId() {
        return _verification;
    }
    MutableLiveData<Boolean> getOtpSent() {
        return _otpSent;
    }
    void signInWithPhoneAuthCredential(String code,Activity activity) {
        if(getVerificationId() != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getVerificationId().getValue().toString(),code );
            Utils.INSTANCE.getFirebaseAuthInstance().signInWithCredential(credential)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                _isSignin.setValue(true);
                            } else {
                                _isSignin.setValue(false);
                            }
                        }
                    });
        }

    }
    void sendOTP(String phoneNumber, Activity activity){
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }
            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                _verification.setValue(verificationId);
                _otpSent.setValue(true);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(Utils.INSTANCE.getFirebaseAuthInstance())
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // (optional) Activity for callback binding
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}

