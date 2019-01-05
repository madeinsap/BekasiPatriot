package com.skripsi.gis.bekasipatriot.activity.ShowMaps;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.skripsi.gis.bekasipatriot.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LatLng current, destination;
    Double lat_cur, lng_cur, lat_des, lng_des;
    String nama, alamat;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    TextView txt_current, txt_destination, txt_waktu, txt_jarak, txt_alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getData();

        txt_current = (TextView) findViewById(R.id.txt_current);
        txt_destination = (TextView) findViewById(R.id.txt_destination);
        txt_waktu = (TextView) findViewById(R.id.txt_waktu);
        txt_jarak = (TextView) findViewById(R.id.txt_jarak);
        txt_alamat = (TextView) findViewById(R.id.txt_alamat);

        txt_current.setText("Lokasi saat ini");
        txt_destination.setText(nama);
        txt_alamat.setText(alamat);
    }

    private void getData() {
        Intent i = getIntent();
        lat_des = i.getDoubleExtra("lat_des", 0);
        lng_des = i.getDoubleExtra("lng_des", 0);
        lat_cur = i.getDoubleExtra("lat_cur", 0);
        lng_cur = i.getDoubleExtra("lng_cur", 0);
        nama = i.getStringExtra("instansi");
        alamat = i.getStringExtra("alamat");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        int height = 55;
        int width = 40;

        current = new LatLng(lat_cur, lng_cur);
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.marker_current);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap current_marker = Bitmap.createScaledBitmap(b, width, height, false);
        mMap.addMarker(new MarkerOptions()
                .position(current)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.fromBitmap(current_marker)));

        destination = new LatLng(lat_des, lng_des);
        BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.marker);
        Bitmap b2=bitmapdraw2.getBitmap();
        Bitmap destination_marker = Bitmap.createScaledBitmap(b2, width, height, false);
        mMap.addMarker(new MarkerOptions()
                .position(destination)
                .title(nama)
                .snippet(alamat)
                .icon(BitmapDescriptorFactory.fromBitmap(destination_marker)));

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(current)
                .include(destination).build();

        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 600, 30));

        String url = getDirectionsUrl();
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    if(j==0){
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){
                        duration = (String)point.get("duration");
                        continue;
                    }
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);
            }
            txt_waktu.setText(duration);
            txt_jarak.setText("(" + distance + ")");

            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(){
        String str_origin = "origin=" + lat_cur + "," + lng_cur;
        String str_dest = "destination=" + lat_des + "," + lng_des;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyBpEK9SDVPk-XkidY67b9QBFi9qbKba0D0";
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
