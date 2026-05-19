package com.example.myapplication;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {

    private MutableLiveData<Boolean> _isSignin = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> getIsSignin() {
        return _isSignin;
    }
    public void signInWithEmail(String email, String password, Activity activity,FirebaseAuth mAuth) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _isSignin.setValue(true);
                    } else {
                        _isSignin.setValue(false);
                        Toast.makeText(activity, "Lỗi đăng nhập: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void signUpWithEmail(String email, String password, Activity activity,FirebaseAuth mAuth) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        _isSignin.setValue(true);
                    } else {
                        _isSignin.setValue(false);
                        Toast.makeText(activity, "Lỗi đăng ký: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void signOut(FirebaseAuth mAuth) {
        mAuth.signOut();
        _isSignin.setValue(false);
    }
}
