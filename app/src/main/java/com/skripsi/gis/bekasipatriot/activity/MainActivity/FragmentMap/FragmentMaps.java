package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.DetailActivity.DetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madeinsap on 3/23/2017.
 */

public class FragmentMaps extends Fragment{

    private ClusterManager<ModelMaps> mClusterManager;
    private static final LatLng TARGET = new LatLng(-6.236320,106.994222);
    private String[] id, instansi, alamat, kategori, telp, gambar, jam, web, jarak;
    private double[] initJarak;
    int numData;
    LatLng latLng[];
    Boolean markerD[];
    private Double[] latitude, longitude;
    Double curentLat, curentLng;
    MapView mMapView;
    private GoogleMap googleMap;

    public FragmentMaps() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getLokasi(){
        String url = "http://insapol.esy.es/skripsi/data/getData.php?lat="+curentLat+"&lng="+curentLng;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                numData = response.length();
                Log.d("DEBUG_", "Parse JSON");
                latLng = new LatLng[numData];
                markerD = new Boolean[numData];
                id = new String[numData];
                instansi = new String[numData];
                alamat = new String[numData];
                jam = new String[numData];
                kategori = new String[numData];
                telp = new String[numData];
                web = new String[numData];
                gambar = new String[numData];
                latitude = new Double[numData];
                longitude = new Double[numData];
                jarak = new String[numData];
                initJarak = new double[numData];

                for (int i = 0; i < numData; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        latLng[i] = new LatLng(data.getDouble("lat"),
                                data.getDouble("lng"));
                        id[i] = data.getString("id");
                        instansi[i] = data.getString("instansi");
                        alamat[i] = data.getString("alamat");
                        jam[i] = data.getString("jam");
                        kategori[i] = data.getString("kategori");
                        telp[i] = data.getString("telp");
                        web[i] = data.getString("web");
                        gambar[i] = data.getString("gambar");
                        latitude[i] = data.getDouble("lat");
                        longitude[i] = data.getDouble("lng");
                        initJarak[i] = Double.parseDouble(data.getString("jarak"));
                        jarak[i] = String.valueOf(round(initJarak[i],2));
                        markerD[i] = false;

                        mClusterManager.addItem(new ModelMaps(latitude[i], longitude[i], instansi[i], alamat[i]));
                    } catch (JSONException je) {

                    }
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TARGET, 10f));
                }

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        for (int i = 0; i < numData; i++){
                            if (marker.getTitle().equals(instansi[i])) {
                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("instansi", instansi[i]);
                                intent.putExtra("alamat", alamat[i]);
                                intent.putExtra("jam", jam[i]);
                                intent.putExtra("tlp", telp[i]);
                                intent.putExtra("gambar", gambar[i]);
                                intent.putExtra("web", web[i]);
                                intent.putExtra("lat_des", latitude[i]);
                                intent.putExtra("lng_des", longitude[i]);
                                intent.putExtra("lat_cur", curentLat);
                                intent.putExtra("lng_cur", curentLng);
                                intent.putExtra("kategori", kategori[i]);
                                intent.putExtra("jarak", jarak[i]);
                                startActivity(intent);
                                markerD[i] = false;
                            } else {
                                markerD[i] = false;
                            }
                        }
                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View exitView = factory.inflate(R.layout.window_refresh, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(getActivity()).create();
                exitDialog.setView(exitView);
                exitDialog.show();

                Button refresh = (Button) exitView.findViewById(R.id.btn_refresh);
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        getLokasi();
                    }
                });
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_maps, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            curentLat = bundle.getDouble("lat", 0.00);
            curentLng = bundle.getDouble("lng", 0.00);
            Log.d("output", "Latitude pada FragMaps = " + curentLat);
            Log.d("output", "Longitude pada FragMaps = " + curentLng);
        }
        if(curentLat==null && curentLng==null){
            Toast.makeText(getActivity(), "Aktifkan GPS, kemudian refresh", Toast.LENGTH_LONG).show();
        }
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        getLokasi();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                mClusterManager = new ClusterManager<>(getActivity(), googleMap);
                RenderClusterInfoWindow renderer = new RenderClusterInfoWindow(getActivity(), googleMap, mClusterManager);
                mClusterManager.setRenderer(renderer);

                googleMap.setOnCameraIdleListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);
                googleMap.setOnInfoWindowClickListener(mClusterManager);

                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ModelMaps>() {
                    @Override
                    public boolean onClusterClick(final Cluster<ModelMaps> cluster) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                cluster.getPosition(), (float) Math.floor(googleMap
                                        .getCameraPosition().zoom + 1.25)), 300,
                                null);
                        return true;
                    }
                });

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
                googleMap.setMyLocationEnabled(true);

                mClusterManager.cluster();
            }
        });
        return view;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private class RenderClusterInfoWindow extends DefaultClusterRenderer<ModelMaps> {

        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<ModelMaps> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onClusterRendered(Cluster<ModelMaps> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<ModelMaps> cluster, MarkerOptions markerOptions) {
            super.onBeforeClusterRendered(cluster, markerOptions);
        }

        @Override
        protected void onBeforeClusterItemRendered(ModelMaps item, MarkerOptions markerOptions) {
            markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.pin));
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}