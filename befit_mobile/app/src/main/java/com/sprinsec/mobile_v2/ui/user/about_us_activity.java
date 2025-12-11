package com.sprinsec.mobile_v2.ui.user;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sprinsec.mobile_v2.R;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sprinsec.mobile_v2.util.Config;

public class about_us_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        try {

            SupportMapFragment supportMapFragment = new SupportMapFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_about_us_map_frame_layout, supportMapFragment);
            fragmentTransaction.commit();

            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    LatLng latLn = new LatLng(Config.COMPANY_ADDRESS_LATITUDE, Config.COMPANY_ADDRESS_LONGITUDE);
                    googleMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                    new CameraPosition.Builder()
                                            .target(latLn)
                                            .zoom(15)
                                            .build()

                            )

                    );
                    googleMap.addMarker(
                            new MarkerOptions()
                                    .position(latLn)
                                    .title(Config.COMPANY_NAME)
                                    .snippet(Config.COMPANY_ADDRESS)


                    );
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    googleMap.getUiSettings().setZoomControlsEnabled(true);


                }
            });

        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        TextView addressText = findViewById(R.id.activity_about_us_address);
        addressText.setText(Config.COMPANY_ADDRESS);

        findViewById(R.id.activity_about_us_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}