package com.myriad.firebase_login_page.login_page;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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
import com.myriad.firebase_login_page.adapter_class.SharedPreferenceClasses;
import com.myriad.firebase_login_page.model_class.Location;
import com.myriad.firebase_login_page.model_class.User;
import com.myriad.sample_library.validation.Validation;

public class LoginController {

    Context context;
    public static String id = "";

    LoginClass loginClass;
    static Dialog dialog;
    TelephonyManager telephonyManager;


    EditText editFirstName, editLastName, editEmail;
    Button btnSave;

    public LoginController(Context context) {
        this.context = context;
    }

    public void loadView(){

        LoginViewClass loginViewClass = new LoginViewClass(context);
        loginViewClass.allowPermission();

        dialog = new Dialog(context,android.R.style.Theme_Holo_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(R.drawable.gradient_bck);
        dialog.setContentView(R.layout.login_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

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

        dialog.show();

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
        LoginClass.deviceId = telephonyManager.getDeviceId();


    }

    private void inputData(){

        LoginClass.email = editEmail.getText().toString().trim();
        LoginClass.firstName = editFirstName.getText().toString().trim();
        LoginClass.lastName = editLastName.getText().toString().trim();

        if (LoginClass.firstName.isEmpty()){
            Toast.makeText(context, "First name required", Toast.LENGTH_SHORT).show();
        }else if (LoginClass.lastName.isEmpty()){
            Toast.makeText(context, "Last name required", Toast.LENGTH_SHORT).show();
        }else if (LoginClass.email.isEmpty()){
            Toast.makeText(context, "Email required", Toast.LENGTH_SHORT).show();
        }else if (!LoginClass.email.matches(Validation.emailPattern)){
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show();
        }else{

            loginClass = new LoginClass(context);
            LoginClass.tree = "User";
            id = String.valueOf(System.currentTimeMillis());
            Location location = new Location(GpsCoordinate.lat,GpsCoordinate.lng,"Thapathali");
            final User user = new User();
            user.setDevice_id(Double.valueOf(LoginClass.deviceId));
            user.setEmail(LoginClass.email);
            user.setFirst_name(LoginClass.firstName);
            user.setLast_name(LoginClass.lastName);
            user.setId(id);
            user.setLocation(location);

            Toast.makeText(context, "Response : "+user, Toast.LENGTH_SHORT).show();

            SharedPreferenceClasses sharedPreferenceClasses = new SharedPreferenceClasses(context);
            sharedPreferenceClasses.saveData(user.getId(),user.getFirst_name(),user.getLast_name(),user.getEmail(),String.format("%.2f",user.getLocation().getLatitude()),String.format("%.2f",user.getLocation().getLongitude()),user.getLocation().getStreet_address(),String.format("%.2f",user.getDevice_id()));

            isDialogDismiss();

        }
    }

    public void isDialogDismiss(){

        if (dialog.isShowing()){
            dialog.dismiss();
        }

    }


}
