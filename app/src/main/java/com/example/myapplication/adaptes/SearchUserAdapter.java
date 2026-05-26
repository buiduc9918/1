package com.example.myapplication.adaptes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ChatActivity2;
import com.example.myapplication.R;
import com.example.myapplication.models.sharesimple.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;

public class SearchUserAdapter extends FirebaseRecyclerAdapter<Users, SearchUserAdapter.UserViewHolder> {

    public SearchUserAdapter(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
        holder.userName.setText(model.getUserName());
        holder.userEmail.setText(model.getEmail());
        holder.userNumber.setText(model.getPhoneNumber());
        holder.itemView.setOnClickListener(v -> {
            // Navigate to ChatActivity2
            Intent intent = new Intent(v.getContext(), ChatActivity2.class);
            intent.putExtra("userId", model.getUid());
            intent.putExtra("userName", model.getUserName());
            v.getContext().startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user, parent, false);
        return new UserViewHolder(view);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userNumber, userEmail;
        public ShapeableImageView imgUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userNumber = itemView.findViewById(R.id.user_number);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
}
