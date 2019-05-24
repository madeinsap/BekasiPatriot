package com.skripsi.gis.bekasipatriot.activity.ActivityListInstansi;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.ActivityListInstansi.Adapter.AdapterInstansi;
import com.skripsi.gis.bekasipatriot.activity.MyApi.ApiClient;
import com.skripsi.gis.bekasipatriot.activity.MyApi.ApiInterface;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Instansi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by madeinsap on 4/17/2017.
 */

public class InstansiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    Double latitude, lngitude;
    String kategori;
    LocationManager locationManager;

    CompositeDisposable compositeDisposable;
    ArrayList<Instansi> listDinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_instansi);

        compositeDisposable = new CompositeDisposable();

        Intent i = getIntent();
        latitude = i.getDoubleExtra("lat", 0);
        lngitude = i.getDoubleExtra("lng", 0);
        kategori = i.getStringExtra("kategori");

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mActionBarToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(kategori);

        swipe = findViewById(R.id.swipe);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView.setAdapter(recyclerViewadapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        swipe.setOnRefreshListener(this);
        swipe.post(() -> {
            recyclerView.setAdapter(null);
            if(compositeDisposable != null) {
                compositeDisposable.clear();
            }
            swipe.setRefreshing(true);
            checkGps();
        });

    }

    void checkGps() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setCancelable(false)
                    .setMessage("Aplikasi membutuhkan GPS")
                    .setPositiveButton("Back", (dialog, id) -> InstansiActivity.super.onBackPressed());
            alertDialogBuilder.show();
        } else {
            lokasi();
        }
    }

    private void lokasi() {
        if (latitude != 0 && lngitude != 0) {
            getData();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(InstansiActivity.this);
            builder.setMessage("Menunggu menemukan lokasi anda");
            builder.setPositiveButton("Refresh",
                    (dialog, which) -> lokasi()
            );
            AlertDialog alert = builder.create();
            alert.show();
            swipe.setRefreshing(false);
        }
    }

    public void getData() {
        swipe.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        switch (kategori) {
            case "Kelurahan":
                compositeDisposable.add(apiService.getKelurahan(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
            case "Kecamatan":
                compositeDisposable.add(apiService.getKecamatan(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
            case "Puskesmas":
                compositeDisposable.add(apiService.getPuskesmas(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
            case "Dinas":
                compositeDisposable.add(apiService.getDinas(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
            case "Pemerintah":
                compositeDisposable.add(apiService.getPemerintah(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
            case "Polisi":
                compositeDisposable.add(apiService.getPolisi(latitude, lngitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse, this::errorResponse));
                break;
        }
    }

    private void errorResponse(Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Internet Connection");
        builder.setPositiveButton("Refresh", (dialog, which) -> getData());
        AlertDialog alert = builder.create();
        alert.show();
        swipe.setRefreshing(false);
    }

    private void handleResponse(List<Instansi> instansis) {
        swipe.setRefreshing(false);
        for (Instansi myInstansi : instansis) {
            double jarak = Double.parseDouble(myInstansi.getJarak());
            myInstansi.setJarak("" + round(jarak) + " Km");
            myInstansi.setLatCur(latitude);
            myInstansi.setLngCur(lngitude);
        }
        listDinas = (ArrayList<Instansi>) instansis;
        recyclerViewadapter = new AdapterInstansi(listDinas, this);
        recyclerView.setAdapter(recyclerViewadapter);
    }

    @Override
    public void onRefresh() {
        lokasi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}