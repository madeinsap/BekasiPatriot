package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by madeinsap on 6/13/2017.
 */

public class ModelMaps implements ClusterItem {

    private final LatLng mPosition;
    private String name;
    private String alamat;

    public ModelMaps(double lat, double lng, String name, String alamat) {
        this.name = name;
        this.alamat = alamat;
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return alamat;
    }
}
