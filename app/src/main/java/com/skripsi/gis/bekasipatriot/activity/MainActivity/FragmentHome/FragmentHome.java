package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.DinasActivity;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.KecamatanActivity;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.KelurahanActivity;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.PemerintahActivity;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.PolisiActivity;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.PuskesmasActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madeinsap on 3/23/2017.
 */

public class FragmentHome extends Fragment {

    ImageButton btnKelurahan;
    ImageButton btnKecamatan;
    ImageButton btnPosyandu;
    ImageButton btnDinas;
    ImageButton btnPemerintah;
    ImageButton btnPolisi;
    View view;

    List<Slider> sliderList = new ArrayList<>();
    RecyclerView sliderRv;
    SliderAdapter sliderAdapter;

    Double lat, lng;

    public FragmentHome() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            lat = bundle.getDouble("lat", 0.00);
            lng = bundle.getDouble("lng", 0.00);
        }

        sliderRv = view.findViewById(R.id.sliderRV);
        btnKelurahan = view.findViewById(R.id.btnKelurahan);
        btnKecamatan = view.findViewById(R.id.btnKecamatan);
        btnPosyandu = view.findViewById(R.id.btnPosyandu);
        btnDinas = view.findViewById(R.id.btnDinas);
        btnPemerintah = view.findViewById(R.id.btnPemerintah);
        btnPolisi = view.findViewById(R.id.btnPolisi);

        btnKelurahan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KelurahanActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnKecamatan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KecamatanActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnPosyandu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PuskesmasActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnDinas.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DinasActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnPemerintah.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PemerintahActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnPolisi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PolisiActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        sliderAdapter = new SliderAdapter(sliderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        sliderRv.setLayoutManager(layoutManager);
        sliderRv.setHasFixedSize(true);
        sliderRv.setAdapter(sliderAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(sliderRv);

        prepareSlider();

        return view;
    }

    private void prepareSlider() {
        Slider slider;
        slider = new Slider("http://www.insapol.esy.es/skripsi/slider/SOROT.png",
                "http://www.bekasikota.go.id");
        sliderList.add(slider);
        slider = new Slider("http://www.insapol.esy.es/skripsi/slider/POT.png",
                "market://details?id="+"id.sorotid.www");
        sliderList.add(slider);
        slider = new Slider("http://www.insapol.esy.es/skripsi/slider/POT1.png",
                "market://details?id="+"id.denz.pengaduanonline");
        sliderList.add(slider);

        sliderAdapter.notifyDataSetChanged();
    }

}