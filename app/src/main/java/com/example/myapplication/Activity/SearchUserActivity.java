package com.example.myapplication.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adaptes.SearchUserAdapter;
import com.example.myapplication.databinding.ActivitySearchUserBinding;
import com.example.myapplication.models.sharesimple.Users;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchUserActivity extends AppCompatActivity {

    public ActivitySearchUserBinding binding;
    private SearchUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup RecyclerView
        binding.searchResultsRv.setLayoutManager(new LinearLayoutManager(this));

        // Setup Toolbar back button
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        // Setup Toolbar Menu Item click
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search) {
                performSearch();
                return true;
            }
            return false;
        });

        // Setup Search Button
        binding.btnSearch.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String email = "";
        String phone = "";
        String name = "";

        if (binding.userEmailEdittext.getText() != null) {
            email = binding.userEmailEdittext.getText().toString().trim();
        }
        if (binding.userSdtEdittext.getText() != null) {
            phone = binding.userSdtEdittext.getText().toString().trim();
        }
        if (binding.userNameEdittext.getText() != null) {
            name = binding.userNameEdittext.getText().toString().trim();
        }

        if (email.isEmpty() && phone.isEmpty() && name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin tìm kiếm", Toast.LENGTH_SHORT).show();
        } else {
            // Xác định tiêu chí tìm kiếm (ưu tiên Tên, rồi đến Email, rồi đến SDT)
            if (!name.isEmpty()) {
                setupSearchRecyclerView("userName", name);
            } else if (!email.isEmpty()) {
                setupSearchRecyclerView("email", email);
            } else {
                setupSearchRecyclerView("phoneNumber", phone);
            }
        }
    }

    private void setupSearchRecyclerView(String field, String searchText) {
        Query query = FirebaseDatabase.getInstance().getReference("AllUsers")
                .child("Users")
                .orderByChild(field)
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();

        if (adapter != null) {
            adapter.stopListening();
        }

        adapter = new SearchUserAdapter(options);
        binding.searchResultsRv.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }
}