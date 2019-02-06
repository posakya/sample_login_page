package com.myriad.firebase_login_page.login_page;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.myriad.firebase_login_page.adapter_class.CheckGpsEnable;
import com.myriad.firebase_login_page.adapter_class.GpsCoordinate;
import com.myriad.firebase_login_page.network.CheckInternet;

public class LoginViewClass {

    Context context;

    public LoginViewClass(Context context) {
        this.context = context;
    }

    CheckGpsEnable checkGpsEnable;
    CheckInternet checkInternet;
    int PERMISSION_ALL = 1;

    public void allowPermission(){

        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE
        };

        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        }

        checkGpsEnable = new CheckGpsEnable(context);
        checkGpsEnable.enableGps();

        checkInternet = new CheckInternet(context);


        if (checkInternet.isNetworkAvailable()) {

            GpsCoordinate gpsCoordinate = new GpsCoordinate(context);
            gpsCoordinate.getLocation();

//            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
//            imageView.setAnimation(animation);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });

        } else {

            Toast.makeText(context, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
