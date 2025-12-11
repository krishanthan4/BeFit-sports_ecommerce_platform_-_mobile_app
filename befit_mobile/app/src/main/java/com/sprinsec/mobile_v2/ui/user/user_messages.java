package com.sprinsec.mobile_v2.ui.user;

import android.app.Notification;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sprinsec.mobile_v2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_messages extends AppCompatActivity {
//    private RecyclerView messagesRecyclerView;
//    private EditText messageInput;
//    private ImageButton sendButton;
//    private TextView userName, userStatus;
//    private ImageView backButton;
//    private CircleImageView profileImage;
//
//    private MessagesAdapter messagesAdapter;
//    private ArrayList<Message> messagesList;
//    private FirebaseFirestore db;
//    private String currentUserId, receiverId;
//    private ListenerRegistration messagesListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_user_messages);
//
//        // Initialize Firebase
//        db = FirebaseFirestore.getInstance();
//        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        receiverId = getIntent().getStringExtra("receiverId"); // Get from intent
//
//        // Initialize views
//        initializeViews();
//
//        // Setup RecyclerView
//        setupRecyclerView();
//
//        // Load user details
//        loadUserDetails();
//
//        // Start listening for messages
//        startMessagesListener();
//
//        // Setup click listeners
//        setupClickListeners();
//    }
//
//    private void initializeViews() {
//        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
//        messageInput = findViewById(R.id.messageInput);
//        sendButton = findViewById(R.id.sendButton);
//        userName = findViewById(R.id.userName);
//        userStatus = findViewById(R.id.userStatus);
//        backButton = findViewById(R.id.backButton);
//        profileImage = findViewById(R.id.profileImage);
//
//        messagesList = new ArrayList<>();
//        messagesAdapter = new MessagesAdapter(messagesList, currentUserId);
//    }
//
//    private void setupRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setStackFromEnd(true);
//        messagesRecyclerView.setLayoutManager(layoutManager);
//        messagesRecyclerView.setAdapter(messagesAdapter);
//    }
//
//    private void setupClickListeners() {
//        sendButton.setOnClickListener(v -> sendMessage());
//        backButton.setOnClickListener(v -> finish());
//        attachButton.setOnClickListener(v -> showAttachmentOptions());
//    }
//
//    private void sendMessage() {
//        String messageText = messageInput.getText().toString().trim();
//        if (!messageText.isEmpty()) {
//            Message message = new Message();
//            message.setMessageId(UUID.randomUUID().toString());
//            message.setSenderId(currentUserId);
//            message.setReceiverId(receiverId);
//            message.setContent(messageText);
//            message.setTimestamp(System.currentTimeMillis());
//            message.setStatus(Message.MessageStatus.SENT);
//            message.setType(Message.MessageType.TEXT);
//
//            // Add to Firestore
//            db.collection("messages")
//                    .document(message.getMessageId())
//                    .set(message)
//                    .addOnSuccessListener(aVoid -> {
//                        messageInput.setText("");
//                        // Scroll to bottom
//                        messagesRecyclerView.smoothScrollToPosition(messagesList.size());
//                    })
//                    .addOnFailureListener(e ->
//                            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
//                    );
//        }
//    }
//
//    private void startMessagesListener() {
//        // Listen for messages where current user is either sender or receiver
//        messagesListener = db.collection("messages")
//                .whereIn("senderId", Arrays.asList(currentUserId, receiverId))
//                .whereIn("receiverId", Arrays.asList(currentUserId, receiverId))
//                .orderBy("timestamp")
//                .addSnapshotListener((value, error) -> {
//                    if (error != null) {
//                        return;
//                    }
//
//                    if (value != null) {
//                        messagesList.clear();
//                        for (QueryDocumentSnapshot doc : value) {
//                            Message message = doc.toObject(Message.class);
//                            messagesList.add(message);
//                        }
//                        messagesAdapter.notifyDataSetChanged();
//                        if (!messagesList.isEmpty()) {
//                            messagesRecyclerView.smoothScrollToPosition(messagesList.size() - 1);
//                        }
//                    }
//                });
//    }
//
//    private void loadUserDetails() {
//        // Load receiver's details
//        db.collection("users").document(receiverId)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        userName.setText(documentSnapshot.getString("name"));
//                        // Load profile image using Glide if available
//                        String profileUrl = documentSnapshot.getString("profileImage");
//                        if (profileUrl != null) {
//                            Glide.with(this)
//                                    .load(profileUrl)
//                                    .placeholder(R.drawable.profile_icon3)
//                                    .into(profileImage);
//                        }
//                    }
//                });
//
//        // Listen for online status
//        db.collection("users").document(receiverId)
//                .addSnapshotListener((value, error) -> {
//                    if (error != null || value == null) return;
//
//                    boolean isOnline = Boolean.TRUE.equals(value.getBoolean("online"));
//                    userStatus.setText(isOnline ? "Online" : "Offline");
//                });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Update user's online status
//        db.collection("users").document(currentUserId)
//                .update("online", true);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Update user's online status
//        db.collection("users").document(currentUserId)
//                .update("online", false);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (messagesListener != null) {
//            messagesListener.remove();
//        }
//    }

    // Messages Adapter inner class
//    private static class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//        private static final int VIEW_TYPE_SENT = 1;
//        private static final int VIEW_TYPE_RECEIVED = 2;
//
//        private final List<Message> messages;
//        private final String currentUserId;
//
//        public MessagesAdapter(List<Message> messages, String currentUserId) {
//            this.messages = messages;
//            this.currentUserId = currentUserId;
//        }
//
//        private static String formatTime(long timestamp) {
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//            return sdf.format(new Date(timestamp));
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            if (viewType == VIEW_TYPE_SENT) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_message_sent, parent, false);
//                return new SentMessageHolder(view);
//            } else {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_message_received, parent, false);
//                return new ReceivedMessageHolder(view);
//            }
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            Message message = messages.get(position);
//
//            if (holder.getItemViewType() == VIEW_TYPE_SENT) {
//                ((SentMessageHolder) holder).bind(message);
//            } else {
//                ((ReceivedMessageHolder) holder).bind(message);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return messages.size();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            Message message = messages.get(position);
//            return message.getSenderId().equals(currentUserId) ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
//        }
//
//        static class SentMessageHolder extends RecyclerView.ViewHolder {
//            TextView messageText, messageTime;
//            ImageView messageStatus;
//
//            SentMessageHolder(View itemView) {
//                super(itemView);
//                messageText = itemView.findViewById(R.id.messageText);
//                messageTime = itemView.findViewById(R.id.messageTime);
//                messageStatus = itemView.findViewById(R.id.messageStatus);
//            }
//
//            void bind(Message message) {
//                messageText.setText(message.getContent());
//                messageTime.setText(formatTime(message.getTimestamp()));
//                // Update status icon based on message status
//                updateStatusIcon(message.getStatus());
//            }
//
//            private void updateStatusIcon(Message.MessageStatus status) {
//                int statusIcon;
//                switch (status) {
//                    case SENT:
//                        statusIcon = R.drawable.ic_message_sent;
//                        break;
//                    case DELIVERED:
//                        statusIcon = R.drawable.ic_message_delivered;
//                        break;
//                    case READ:
//                        statusIcon = R.drawable.ic_message_read;
//                        break;
//                    default:
//                        statusIcon = R.drawable.ic_message_sent;
//                }
//                messageStatus.setImageResource(statusIcon);
//            }
//        }
//
//        static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
//            TextView messageText, messageTime;
//
//            ReceivedMessageHolder(View itemView) {
//                super(itemView);
//                messageText = itemView.findViewById(R.id.messageText);
//                messageTime = itemView.findViewById(R.id.messageTime);
//            }
//
//            void bind(Message message) {
//                messageText.setText(message.getContent());
//                messageTime.setText(formatTime(message.getTimestamp()));
//            }
//        }
//    }
}