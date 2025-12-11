package com.sprinsec.mobile_v2.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class CommonFunctions {
    public static void backToHome(ImageView image, Context context, Class<?> destinationClass) {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, destinationClass));
            }
        });
    }

}
