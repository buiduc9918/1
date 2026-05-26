package com.example.myapplication.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityChat2Binding;

public class ChatActivity2 extends AppCompatActivity {

    private ActivityChat2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        binding = ActivityChat2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setuptoolbar();


    }
    void  setuptoolbar(){
        // Xử lý nút quay lại trên toolbar
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        // Lấy thông tin người dùng từ Intent
        String userName = getIntent().getStringExtra("userName");
        if (userName != null) {
            binding.toolbar.setTitle(userName);
        }
    }

}