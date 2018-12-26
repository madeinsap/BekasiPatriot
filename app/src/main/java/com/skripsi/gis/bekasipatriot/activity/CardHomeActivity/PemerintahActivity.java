package com.skripsi.gis.bekasipatriot.activity.CardHomeActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.Adapter.AdapterCard;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.Model.ModelCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madeinsap on 4/17/2017.
 */

public class PemerintahActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    List<ModelCard> GetDataAdapter1 = new ArrayList<>();

    SwipeRefreshLayout swipe;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://insapol.esy.es/skripsi/data/haverslinePemerintah.php?lat=";
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    Double latitude, lngitude;
    LocationManager locationManager;

    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_instansi);

        Intent i = getIntent();
        latitude = i.getDoubleExtra("lat", 0);
        lngitude = i.getDoubleExtra("lng", 0);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mActionBarToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pemerintah");

        GetDataAdapter1 = new ArrayList<>();

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new AdapterCard(GetDataAdapter1, this);
        recyclerView.setAdapter(recyclerViewadapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           lokasi();
                       }
                   }
        );

    }

    private void lokasi() {
        swipe.setRefreshing(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setCancelable(false)
                    .setMessage("Aplikasi membutuhkan GPS")
                    .setPositiveButton("Back",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int id) {
                            PemerintahActivity.super.onBackPressed();
                        }
                    });
            alertDialogBuilder.show();
        }

        else{
            if (latitude != null && lngitude != null) {
                JSON_DATA_WEB_CALL();
            }

            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(PemerintahActivity.this);
                builder.setMessage("Menunggu menemukan lokasi anda");
                builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lokasi();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                swipe.setRefreshing(false);
            }
        }
    }

    public void JSON_DATA_WEB_CALL() {
        GetDataAdapter1.clear();
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL + latitude + "&lng=" + lngitude,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        swipe.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PemerintahActivity.this);
                        builder.setMessage("No Internet Connection");
                        builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lokasi();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        swipe.setRefreshing(false);
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            ModelCard GetDataAdapter2 = new ModelCard();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setInstansi(json.getString("instansi"));
                GetDataAdapter2.setGambar(json.getString("gambar"));
                GetDataAdapter2.setAlamat(json.getString("alamat"));
                GetDataAdapter2.setTelp(json.getString("telp"));
                GetDataAdapter2.setJam(json.getString("jam"));
                GetDataAdapter2.setLat(json.getDouble("lat"));
                GetDataAdapter2.setLng(json.getDouble("lng"));
                GetDataAdapter2.setLat_cur(latitude);
                GetDataAdapter2.setLng_cur(lngitude);
                GetDataAdapter2.setKategori(json.getString("kategori"));
                GetDataAdapter2.setWeb(json.getString("web"));

                double jarak = Double.parseDouble(json.getString("jarak"));
                GetDataAdapter2.setJarak("" + round(jarak, 2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }
        recyclerViewadapter = new AdapterCard(GetDataAdapter1, this);
        recyclerView.setAdapter(recyclerViewadapter);
    }

    @Override
    public void onRefresh() {
        lokasi();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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
}