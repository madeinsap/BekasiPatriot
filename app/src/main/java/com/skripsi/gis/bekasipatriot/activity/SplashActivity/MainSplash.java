package com.skripsi.gis.bekasipatriot.activity.SplashActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.MainActivity.HomeActivity;

public class MainSplash extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 1;
    //private static final int REQUEST_CALL_LOCATION = 1;
    private final int SPLASH = 5000;
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            if(!checkPermission()) {
                requestPermission();
            }else {
                startMain();
            }
        }
        else{
            startMain();
        }
        ProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    void startMain(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainSplash.this, HomeActivity.class);
                MainSplash.this.startActivity(mainIntent);
                MainSplash.this.finish();
            }
        }, SPLASH);
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainSplash.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }


    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainSplash.this,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(MainSplash.this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainSplash.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(MainSplash.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startMain();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainSplash.this);
                    alertDialogBuilder
                            .setMessage("Aktifkan Izin Lokasi")
                            .setCancelable(false)
                            .setPositiveButton("Pengaturan",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Keluar",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    finish();
                                    System.exit(0);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
        }
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    */
}
