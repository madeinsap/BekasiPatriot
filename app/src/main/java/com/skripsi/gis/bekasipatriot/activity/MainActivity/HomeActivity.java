package com.skripsi.gis.bekasipatriot.activity.MainActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.AboutActivity.AboutActivity;
import com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentHome.FragmentHome;
import com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentMap.FragmentMaps;
import com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentNews.FragmentNews;
import com.skripsi.gis.bekasipatriot.activity.Tips_Activity.TipsActivity;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    double latitude, longitude;

    Criteria criteria;
    Location location;
    LocationManager locationManager;
    String provider;
    int indexNavigation;

    Toolbar toolbarMain;
    BottomNavigationView myNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbar_main);
        toolbarMain.setTitle(R.string.app_name);
        setSupportActionBar(toolbarMain);

        myNavigation = findViewById(R.id.navigation);
        myNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        cekGPS();
        getLokasi();

        loadFragment(new FragmentHome());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!(getSupportFragmentManager().findFragmentById(R.id.main_content) instanceof FragmentHome)) {
                        fragment = new FragmentHome();
                        loadFragment(fragment);
                    }
                    return true;
                case R.id.navigation_map:
                    if (!(getSupportFragmentManager().findFragmentById(R.id.main_content) instanceof FragmentMaps)) {
                        fragment = new FragmentMaps();
                        loadFragment(fragment);
                    }
                    return true;
                case R.id.navigation_news:
                    if (!(getSupportFragmentManager().findFragmentById(R.id.main_content) instanceof FragmentNews)) {
                        fragment = new FragmentNews();
                        loadFragment(fragment);
                    }
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lng", longitude);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void cekGPS(){
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        }
        else{
            Toast.makeText(this, "GPS Aktif", Toast.LENGTH_SHORT).show();
        }
    }

    private void onGPS(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setCancelable(false)
                .setMessage("GPS Tidak Aktif")
                .setPositiveButton("Pengaturan",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                    }
                });
        alertDialogBuilder.show();
    }

    private void getLokasi(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 1000, 0, this);

        if(location!=null){
            onLocationChanged(location);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_tips) {
            Intent intent = new Intent(this, TipsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        final FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 1) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    HomeActivity.this);

            alertDialogBuilder
                    .setMessage("Keluar aplikasi?")
                    .setCancelable(true)
                    .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            HomeActivity.this.finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            fm.popBackStack();
            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    myNavigation.getMenu().getItem(onCheckFragment(fm)).setChecked(true);
                }
            });
        }
    }

    public int onCheckFragment(FragmentManager fm){
        if (fm.findFragmentById
                (R.id.main_content) instanceof FragmentHome) {
            indexNavigation = 0;
        } else if(fm.findFragmentById
                (R.id.main_content) instanceof FragmentMaps) {
            indexNavigation = 1;
        } else if(fm.findFragmentById
                (R.id.main_content) instanceof FragmentNews) {
            indexNavigation = 2;
        }

        return indexNavigation;
    }

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
