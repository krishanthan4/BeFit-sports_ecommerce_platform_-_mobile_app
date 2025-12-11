package com.sprinsec.mobile_v2.ui.fragments.admin;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.util.Config;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class admin_dashboard_promotions_and_offers extends Fragment {
    private ImageView fragment_admin_dashboard_promotions_and_offers_success_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_promotions_and_offers, container, false);
        EditText titleInputField = view.findViewById(R.id.promotions_and_offers_title_input_field);
        EditText messageInputField = view.findViewById(R.id.promotions_and_offers_message_input_field);
        Button sendButton = view.findViewById(R.id.promotions_and_offers_send_button);
        fragment_admin_dashboard_promotions_and_offers_success_image = view.findViewById(R.id.fragment_admin_dashboard_promotions_and_offers_success_image);

        sendButton.setOnClickListener(v -> {

            String title = titleInputField.getText().toString().trim();
            String message = messageInputField.getText().toString().trim();

            if (!title.isEmpty() && !message.isEmpty()) {
              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                          MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    JsonObject jsonObject = new JsonObject();
                          jsonObject.addProperty("title", title);
                          jsonObject.addProperty("message", message);
                          jsonObject.addProperty("topic","all_users");

                          String json = new Gson().toJson(jsonObject);
                          RequestBody body = RequestBody.create(json, JSON);



                          Request request = new Request.Builder()
                                  .url(Config.BACKEND_API_URL+"sendNotificationsProcess.php")
                                  .post(body)
                                  .build();

                          Response response=okHttpClient.newCall(request).execute();
                     String responseJson = response.body().string();
                     JSONObject responseObject = new JSONObject(responseJson);
                     if(responseObject.has("success") && responseObject.getBoolean("success")){

                            titleInputField.setText("");
                            messageInputField.setText("");
                              FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                              HashMap<String, String> document = new HashMap<>();
                              document.put("message_title", title);
                              document.put("message_body", message);
                              document.put("timestamp", String.valueOf(System.currentTimeMillis()));

                              firebaseFirestore.collection("notification")
                                      .add(document)
                                      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                          @Override
                                          public void onSuccess(DocumentReference documentReference) {
                                              fragment_admin_dashboard_promotions_and_offers_success_image.setVisibility(View.VISIBLE);
                                              Glide.with(getContext()).load(R.drawable.success).into(fragment_admin_dashboard_promotions_and_offers_success_image);
                                              view.findViewById(R.id.promotions_and_offers_card).setVisibility(View.GONE);

                                              new Thread(() -> {
                                                  try {
                                                      Thread.sleep(3000);
                                                      getActivity().runOnUiThread(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              fragment_admin_dashboard_promotions_and_offers_success_image.setVisibility(View.GONE);
                                                              view.findViewById(R.id.promotions_and_offers_card).setVisibility(View.VISIBLE);
                                                          }
                                                      });
                                                  } catch (InterruptedException e) {
                                                      e.printStackTrace();
                                                  }
                                              }).start();

                                              Log.i("Firestore Success", "Notification added");
                                          }
                                      })
                                      .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Log.e("Firestore Error", e.getMessage());
                                          }
                                      });                          }
                      } catch (Exception e) {
                        Log.i("befit_logs", "admin_dashboard_promotions_and_offers : "+e.getMessage());
                      }
                  }
              }).start();

                } else {
                Toast.makeText(getContext(), "Title and message cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

}