package com.skripsi.gis.bekasipatriot.activity.ActivityMain.FragmentHome;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.ActivityListInstansi.InstansiActivity;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by madeinsap on 3/23/2017.
 */

public class FragmentHome extends Fragment {

    LocationManager locationManager;
    LocationListener locationListener;

    ImageButton btnKelurahan;
    ImageButton btnKecamatan;
    ImageButton btnPosyandu;
    ImageButton btnDinas;
    ImageButton btnPemerintah;
    ImageButton btnPolisi;

    List<Slider> sliderList = new ArrayList<>();

    ViewPager viewPager;
    SliderAdapter sliderAdapter;

    Double lat, lng;

    public FragmentHome() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            lat = bundle.getDouble("lat", 0.00);
            lng = bundle.getDouble("lng", 0.00);
        }

        cekGps();

        viewPager = view.findViewById(R.id.pagerSlider);
        viewPager.setClipChildren(false);
        viewPager.setPageMargin(32);
        viewPager.setPageTransformer(false, new SliderAnimation(getActivity()));

        btnKelurahan = view.findViewById(R.id.btnKelurahan);
        btnKecamatan = view.findViewById(R.id.btnKecamatan);
        btnPosyandu = view.findViewById(R.id.btnPosyandu);
        btnDinas = view.findViewById(R.id.btnDinas);
        btnPemerintah = view.findViewById(R.id.btnPemerintah);
        btnPolisi = view.findViewById(R.id.btnPolisi);

        btnKelurahan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Kelurahan");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnKecamatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Kecamatan");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnPosyandu.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Puskesmas");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnDinas.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Dinas");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnPemerintah.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Pemerintah");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnPolisi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InstansiActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("kategori", "Polisi");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        prepareSlider();
        sliderAdapter = new SliderAdapter(getActivity(), sliderList);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int curr = viewPager.getCurrentItem();
                    int lastReal = Objects.requireNonNull(viewPager.getAdapter()).getCount() - 2;
                    if (curr == 0) {
                        viewPager.setCurrentItem(lastReal, true);
                    } else if (curr > lastReal) {
                        viewPager.setCurrentItem(1, true);
                    }
                }
            }
        });

        return view;
    }

    private void cekGps() {
        locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        locationListener = new myLocationListener();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        } else {
            getLokasi();
        }
    }

    private void prepareSlider() {
        sliderList.add(new Slider("http://www.insapol.esy.es/skripsi/slider/POT1.png",
                "market://details?id=" + "id.denz.pengaduanonline"));
        sliderList.add(new Slider("http://www.insapol.esy.es/skripsi/slider/SOROT.png",
                "http://www.bekasikota.go.id"));
        sliderList.add(new Slider("http://www.insapol.esy.es/skripsi/slider/POT.png",
                "market://details?id=" + "id.sorotid.www"));
        sliderList.add(new Slider("http://www.insapol.esy.es/skripsi/slider/POT1.png",
                "market://details?id=" + "id.denz.pengaduanonline"));
        sliderList.add(new Slider("http://www.insapol.esy.es/skripsi/slider/SOROT.png",
                "http://www.bekasikota.go.id"));
    }

    private void onGPS() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
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

    private void getLokasi() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private class myLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();
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