package com.myriad.firebase_login_page.adapter_class;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClasses {

    Context context;
    SharedPreferences sp;
    public static String FirstName,LastName,Email,DeviceId,Id,Latitude,Longitude,Street_address;

    public SharedPreferenceClasses(Context context) {
        this.context = context;

    }

    public void saveData(String id,String firstname,String lastname,String email,String lat,String lng,String street_address,String device_id){

        sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id",id);
        editor.putString("first_name",firstname);
        editor.putString("last_name",lastname);
        editor.putString("email",email);
        editor.putString("street_address",street_address);
        editor.putString("latitude",lat);
        editor.putString("longitude",lng);
        editor.putString("device_id",device_id);
        editor.apply();


    }

    public void retrieveData(){

        sp = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        FirstName = sp.getString("first_name",null);
        LastName = sp.getString("last_name",null);
        Email = sp.getString("email",null);
        DeviceId = sp.getString("device_id",null);
        Id = sp.getString("id",null);
        Latitude = sp.getString("latitude",null);
        Longitude = sp.getString("longitude",null);
        Street_address = sp.getString("street_address",null);


    }



}
