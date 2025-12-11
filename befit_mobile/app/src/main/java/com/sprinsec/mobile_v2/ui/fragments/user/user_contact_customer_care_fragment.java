package com.sprinsec.mobile_v2.ui.fragments.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.util.Config;

public class user_contact_customer_care_fragment extends Fragment {

    private static final int CALL_PERMISSION_REQUEST = 100;
    private final String phoneNumber = Config.COMPANY_PHONE;

    private void requestCallPermission() {
        if (getContext() == null) return;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            openDialer(); // Open dialer if permission is already granted
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openDialer(); // Permission granted, open dialer
            } else {
                Toast.makeText(getContext(), "Permission denied to access the dialer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__contact_customer_care, container, false);

        FrameLayout contactButton = view.findViewById(R.id.fragment_contact_customer_care_mobile);
        contactButton.setOnClickListener(v -> requestCallPermission());

        ImageView fragment_contact_customer_care_back_icon = view.findViewById(R.id.fragment_contact_customer_care_back_icon);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment_contact_customer_care_back_icon.setOnClickListener(v -> {
            fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_profile_fragment.class, null).commit();
        });

        try {
            view.findViewById(R.id.fragment_contact_customer_care_email).setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Config.COMPANY_EMAIL));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Customer Care");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello, I need help with ...");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error opening Email client. Ensure you have Gmail application.!", Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
        }
        return view;
    }
}
