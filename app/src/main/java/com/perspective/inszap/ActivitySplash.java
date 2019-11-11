package com.perspective.inszap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivitySplash extends AppCompatActivity {
    private static int PERMISSION_REQUEST_CODE = 1234;
    String[] appPermissions = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       checkAndRequestPermissions();
        Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);




    }
    public boolean checkAndRequestPermissions(){
        List<String> ListPermissionsNeeded = new ArrayList<>();
        for(String perm : appPermissions){
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
            {
                ListPermissionsNeeded.add(perm);

            }
        }
        if(!ListPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,ListPermissionsNeeded.toArray(new String[ListPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }
}
