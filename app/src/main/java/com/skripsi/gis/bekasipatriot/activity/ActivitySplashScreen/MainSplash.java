package com.skripsi.gis.bekasipatriot.activity.ActivitySplashScreen;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.ActivityMain.HomeActivity;

public class MainSplash extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    ProgressBar ProgressBar;
    LocationManager locationManager;
    LocationListener locationListener;
    Double latitude = 0.0;
    Double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission();
            } else {
                getCurrentLocation();
            }
        } else {
            getCurrentLocation();
        }
        ProgressBar = findViewById(R.id.progressBar);
    }

    private void getCurrentLocation() {
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        startMain();
    }

    private void startMain() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (latitude.equals(0.0) && longitude.equals(0.0)) {
                    latitude = -6.242537;
                    longitude = 106.999763;

                    Toast.makeText(MainSplash.this, "Menggunakan lokasi default", Toast.LENGTH_SHORT).show();
                }

                Intent mainIntent = new Intent(MainSplash.this, HomeActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("lat", latitude);
                mainIntent.putExtra("lng", longitude);
                startActivity(mainIntent);
                MainSplash.this.finish();
            }
        }, 5000);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainSplash.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions
                (MainSplash.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cekGPS();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainSplash.this);
                    alertDialogBuilder
                            .setMessage("Aplikasi tidak diizinkan mengakses GPS")
                            .setCancelable(false)
                            .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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

    public void cekGPS() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        } else {
            getCurrentLocation();
        }
    }

    private void onGPS() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setCancelable(false)
                .setMessage("GPS Tidak Aktif")
                .setPositiveButton("Pengaturan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cekGPS();
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
