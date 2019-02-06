package com.myriad.firebase_login_page.login_page;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myriad.firebase_login_page.R;
import com.myriad.firebase_login_page.adapter_class.GpsCoordinate;
import com.myriad.firebase_login_page.adapter_class.LoginClass;

public class LoginController {

    Context context;
    public static String id = "";

    LoginClass loginClass;

    TelephonyManager telephonyManager;
    public static String deviceId;
    public static String firstName;
    public static String lastName;
    public static String email;

    EditText editFirstName, editLastName, editEmail;
    Button btnSave;

    public LoginController(Context context) {
        this.context = context;
    }

    public void loadView(){

        LoginViewClass loginViewClass = new LoginViewClass(context);
        loginViewClass.allowPermission();

        Dialog dialog = new Dialog(context,android.R.style.Theme_Holo_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(R.color.colorPrimaryDark);
        dialog.setContentView(R.layout.login_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.setCanceledOnTouchOutside(true);

        editEmail = dialog.findViewById(R.id.editEmail);
        editFirstName = dialog.findViewById(R.id.editFirstName);
        editLastName = dialog.findViewById(R.id.editLastName);
        btnSave = dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();


    }

    public void inputData(){
        email = editEmail.getText().toString().trim();
        firstName = editFirstName.getText().toString().trim();
        lastName = editLastName.getText().toString().trim();

        if (firstName.isEmpty()){
            Toast.makeText(context, "First name required", Toast.LENGTH_SHORT).show();
        }else if (lastName.isEmpty()){
            Toast.makeText(context, "Last name required", Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty()){
            Toast.makeText(context, "Email required", Toast.LENGTH_SHORT).show();
        }else{
            loginClass = new LoginClass(context);
            LoginClass.tree = "User";
            loginClass.postData(LoginClass.tree,id,firstName,lastName,email, Double.valueOf(deviceId), GpsCoordinate.lat, GpsCoordinate.lng,"imadol");
        }
    }


}
