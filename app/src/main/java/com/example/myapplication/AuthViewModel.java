package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class AuthViewModel extends ViewModel {
    private MutableLiveData<String> _verification = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> _otpSent = new MutableLiveData<>(false);
    public MutableLiveData<String> getVerificationId() {
        return _verification;
    }

    public MutableLiveData<Boolean> getOtpSent() {
        return _otpSent;
    }

    void sendOTP(String phoneNumber, Activity activity){
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                _verification.setValue(verificationId);
                _otpSent.setValue(true);
            }
        };

        void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getVerificationId(), code);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            } else {

                            }
                        }
                    });
        }
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

