package com.example.myapplication.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.models.sharesimple.ChatMessageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, ChatAdapter.ChatViewHolder> {

    public ChatAdapter(@NonNull FirebaseRecyclerOptions<ChatMessageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessageModel model) {
        String currentUserId = Utils.INSTANCE.getUserID();
        
        String time = formatTime(model.getTimestamp());

        if (model.getSenderId() != null && model.getSenderId().equals(currentUserId)) {
            // Tin nhắn của tôi: hiện bên phải, ẩn bên trái
            holder.leftHolder.setVisibility(View.GONE);
            holder.rightHolder.setVisibility(View.VISIBLE);
            holder.rightMessage.setText(model.getMessage());
            holder.rightTime.setText(time);
        } else {
            // Tin nhắn người khác: hiện bên trái, ẩn bên phải
            holder.rightHolder.setVisibility(View.GONE);
            holder.leftHolder.setVisibility(View.VISIBLE);
            holder.leftMessage.setText(model.getMessage());
            holder.leftTime.setText(time);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new ChatViewHolder(view);
    }

    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public CardView leftHolder, rightHolder;
        public TextView leftMessage, rightMessage;
        public TextView leftTime, rightTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            leftHolder = itemView.findViewById(R.id.leftHolder);
            rightHolder = itemView.findViewById(R.id.rightHolder);
            leftMessage = itemView.findViewById(R.id.txt_received_message);
            rightMessage = itemView.findViewById(R.id.txt_sent_message);
            leftTime = itemView.findViewById(R.id.txt_received_time);
            rightTime = itemView.findViewById(R.id.txt_sent_time);
        }
    }
}
