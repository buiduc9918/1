package com.example.myapplication.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.Query;
import com.example.myapplication.adaptes.MessageAdapter;
import com.example.myapplication.databinding.ActivityChat2Binding;
import com.example.myapplication.models.sharesimple.ChatMessageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity2 extends AppCompatActivity {

    private ActivityChat2Binding binding;

    String chatRoomID = "";
    private MessageAdapter adapter;

    private void setupRecyclerView() {
        if (chatRoomID.isEmpty()) return;

        com.google.firebase.database.Query query = FirebaseDatabase.getInstance()
                .getReference("ChatRoom").child(chatRoomID).child("messages");

        FirebaseRecyclerOptions<ChatMessageModel> options =
                new FirebaseRecyclerOptions.Builder<ChatMessageModel>()
                        .setQuery(query, ChatMessageModel.class)
                        .build();

        adapter = new MessageAdapter(options);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecyclerView.setAdapter(adapter);

        // Tự động cuộn xuống khi có tin nhắn mới
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                binding.chatRecyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) adapter.stopListening();
    }

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
        creatGetChatRoom();
        binding.btnSend.setOnClickListener(v -> {
            String message = binding.editMessage.getText().toString().trim();
            if (message.isEmpty()) {
                binding.editMessage.setError("Enter a message before sending");
            } else {
                sendMessage(message);
                binding.editMessage.setText("");
            }
        });
        setupRecyclerView();
    }

    private void sendMessage(String message) {
        String myId = com.example.myapplication.Utils.INSTANCE.getUserID();
        if (chatRoomID.isEmpty() || myId.isEmpty()) return;

        com.google.firebase.database.DatabaseReference chatRoomRef = com.google.firebase.database.FirebaseDatabase.getInstance()
                .getReference("ChatRoom").child(chatRoomID);

        // Update last message time and sender only
        chatRoomRef.child("lastMessageTimestamp").setValue(System.currentTimeMillis());
        chatRoomRef.child("lastMessageSenderId").setValue(myId);
        chatRoomRef.child("lastMessage").setValue(message);

        // Create and send new message
        com.example.myapplication.models.sharesimple.ChatMessageModel messageModel = 
            new com.example.myapplication.models.sharesimple.ChatMessageModel(message, myId, System.currentTimeMillis());

        chatRoomRef.child("messages").push().setValue(messageModel);

        // Clear input field
        binding.editMessage.setText("");
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
    void creatGetChatRoom() {
        String myId = com.example.myapplication.Utils.INSTANCE.getUserID();
        String receiverId = getIntent().getStringExtra("userId");

        if (receiverId != null && !myId.isEmpty()) {
            chatRoomID = getChatRoomId(myId, receiverId);

            com.google.firebase.database.DatabaseReference chatRoomRef = com.google.firebase.database.FirebaseDatabase.getInstance()
                    .getReference("ChatRoom").child(chatRoomID);

            chatRoomRef.get().addOnSuccessListener(snapshot -> {
                if (!snapshot.exists()) {
                    createNewChatRoom(chatRoomRef, myId, receiverId);
                }
            }).addOnFailureListener(e -> {
                // Nếu lỗi khi lấy dữ liệu, thử tạo mới phòng chat như yêu cầu
                createNewChatRoom(chatRoomRef, myId, receiverId);
            });
        }
    }

    private void createNewChatRoom(com.google.firebase.database.DatabaseReference chatRoomRef, String myId, String receiverId) {
        java.util.ArrayList<String> userIds = new java.util.ArrayList<>();
        userIds.add(myId);
        userIds.add(receiverId);

        com.example.myapplication.models.sharesimple.ChatRoomModel chatRoom = new com.example.myapplication.models.sharesimple.ChatRoomModel();
        chatRoom.setChatRoomId(chatRoomID);
        chatRoom.setUserIds(userIds);
        chatRoom.setLastMessageTimestamp(System.currentTimeMillis());
        chatRoom.setLastMessage("");

        chatRoomRef.setValue(chatRoom);
    }
    private String getChatRoomId(String u1, String u2) {
        if (u1.hashCode() < u2.hashCode()) {
            return u1 + "_" + u2;
        } else {
            return u2 + "_" + u1;
        }
    }



}