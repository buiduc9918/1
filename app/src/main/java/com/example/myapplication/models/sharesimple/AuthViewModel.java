package com.example.myapplication.models.sharesimple;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {

    private MutableLiveData<Boolean> _isSignin = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> _isCurrentUser = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getIsSignin() {
        return _isSignin;
    }

    public MutableLiveData<Boolean> getIsCurrentUser() {
        return _isCurrentUser;
    }

    // Constructor trong Java tương đương với khối 'init' trong Kotlin
    public AuthViewModel() {
        // Kiểm tra xem đã có người dùng đăng nhập chưa ngay khi khởi tạo
        if (Utils.INSTANCE.getFirebaseAuthInstance().getCurrentUser() != null) {
            _isCurrentUser.setValue(true);
        }
    }

    public void signInWithEmail(String email, String password, Activity activity, FirebaseAuth mAuth) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _isSignin.setValue(true);
                        _isCurrentUser.setValue(true);
                    } else {
                        _isSignin.setValue(false);
                        Toast.makeText(activity, "Lỗi đăng nhập: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signUpWithEmail(String email, String password, Activity activity, FirebaseAuth mAuth) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        _isSignin.setValue(true);
                        _isCurrentUser.setValue(true);
                    } else {
                        _isSignin.setValue(false);
                        Toast.makeText(activity, "Lỗi đăng ký: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signOut(FirebaseAuth mAuth) {
        mAuth.signOut();
        _isSignin.setValue(false);
        _isCurrentUser.setValue(false);
    }
}
