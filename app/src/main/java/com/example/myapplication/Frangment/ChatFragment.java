package com.example.myapplication.Frangment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.adaptes.SearchUserAdapter;
import com.example.myapplication.databinding.FragmentChatBinding;
import com.example.myapplication.models.sharesimple.Users;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private SearchUserAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);

        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        // Hiện tại hiển thị danh sách tất cả người dùng để bạn có thể chọn bắt đầu chat
        // Sau này bạn có thể thay đổi Query này để chỉ hiện các cuộc hội thoại gần đây
        Query query = FirebaseDatabase.getInstance().getReference("AllUsers")
                .child("Users")
                .limitToFirst(50);

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();

        adapter = new SearchUserAdapter(options);
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewChat.setAdapter(adapter);
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