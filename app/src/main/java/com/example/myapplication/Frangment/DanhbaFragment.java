package com.example.myapplication.Frangment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.adaptes.SearchUserAdapter;
import com.example.myapplication.databinding.FragmentDanhbaBinding;
import com.example.myapplication.models.sharesimple.Users;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DanhbaFragment extends Fragment {
    private FragmentDanhbaBinding binding;
    private SearchUserAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDanhbaBinding.inflate(inflater, container, false);

        setupRecyclerView("");

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setupRecyclerView(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return binding.getRoot();
    }

    private void setupRecyclerView(String searchText) {
        Query query;
        if (searchText.isEmpty()) {
            query = FirebaseDatabase.getInstance().getReference("AllUsers")
                    .child("Users")
                    .limitToFirst(50); // Show some users by default
        } else {
            query = FirebaseDatabase.getInstance().getReference("AllUsers")
                    .child("Users")
                    .orderByChild("userName")
                    .startAt(searchText)
                    .endAt(searchText + "\uf8ff");
        }

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();

        if (adapter != null) {
            adapter.stopListening();
        }

        adapter = new SearchUserAdapter(options);
        binding.recyclerViewDanhBa.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDanhBa.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}