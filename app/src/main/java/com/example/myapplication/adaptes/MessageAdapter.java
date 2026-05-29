package com.example.myapplication.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.models.sharesimple.ChatMessageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, MessageAdapter.MessageViewHolder> {

    public MessageAdapter(@NonNull FirebaseRecyclerOptions<ChatMessageModel> options) {
        super(options);
    }
    private MessageAdapter adapter;
    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull ChatMessageModel model) {
        String currentUserId = Utils.INSTANCE.getUserID();

        if (model.getSenderId() != null && model.getSenderId().equals(currentUserId)) {
            // TIN NHẮN CỦA TÔI: Hiện bên phải, ẩn bên trái
            holder.layoutSent.setVisibility(View.VISIBLE);
            holder.layoutReceived.setVisibility(View.GONE);

            holder.txtSentMessage.setText(model.getMessage());
            holder.txtSentTime.setText(formatTime(model.getTimestamp()));
        } else {
            // TIN NHẮN NGƯỜI KHÁC: Hiện bên trái, ẩn bên phải
            holder.layoutReceived.setVisibility(View.VISIBLE);
            holder.layoutSent.setVisibility(View.GONE);

            holder.txtReceivedMessage.setText(model.getMessage());
            holder.txtReceivedTime.setText(formatTime(model.getTimestamp()));
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MessageViewHolder(view);
    }

    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        View layoutReceived, layoutSent;
        TextView txtReceivedMessage, txtReceivedTime;
        TextView txtSentMessage, txtSentTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutReceived = itemView.findViewById(R.id.layout_received);
            layoutSent = itemView.findViewById(R.id.layout_sent);

            txtReceivedMessage = itemView.findViewById(R.id.txt_received_message);
            txtReceivedTime = itemView.findViewById(R.id.txt_received_time);

            txtSentMessage = itemView.findViewById(R.id.txt_sent_message);
            txtSentTime = itemView.findViewById(R.id.txt_sent_time);
        }
    }
}