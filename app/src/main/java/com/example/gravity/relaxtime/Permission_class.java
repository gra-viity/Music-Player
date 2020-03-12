package com.example.gravity.relaxtime;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Permission_class extends AppCompatActivity {
    Button proceed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_class);
        proceed=findViewById(R.id.proceed);
        permission();

    }

    private void permission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    Intent intent=new Intent(Permission_class.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    if(response.isPermanentlyDenied()){
                        showSettingDialoge();
                        Log.v("he","denied settings are working");
                    }
                }
                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();

//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.READ_PHONE_STATE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        Intent intent=new Intent(Permission_class.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if(response.isPermanentlyDenied()){
//                            showSettingDialoge();
//                        }
//                    }
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();

         }

    private void showSettingDialoge() {
        Log.v("he","permission settings method is working");
        AlertDialog.Builder builder=new AlertDialog.Builder(Permission_class.this);;
        builder.setTitle("Need this permission")
                .setMessage("This app needs permisson to use music player.You can grant them in app settings")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSettings();
                        dialog.cancel();
                    }

                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
