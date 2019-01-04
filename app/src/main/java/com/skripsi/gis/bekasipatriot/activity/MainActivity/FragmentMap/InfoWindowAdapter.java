package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.skripsi.gis.bekasipatriot.R;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInflater mInflater;

    public InfoWindowAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = mInflater.inflate(R.layout.custom_info_window, null);

        ModelMaps modelMaps = (ModelMaps) marker.getTag();

        ((TextView) view.findViewById(R.id.txt_nama_instansi)).setText(marker.getTitle());
        ((TextView) view.findViewById(R.id.txt_alamat_instansi)).setText(marker.getSnippet());

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = mInflater.inflate(R.layout.custom_info_window, null);

        ((TextView) view.findViewById(R.id.txt_nama_instansi)).setText(marker.getTitle());
        ((TextView) view.findViewById(R.id.txt_alamat_instansi)).setText(marker.getSnippet());

        return view;
    }
}
