package com.skripsi.gis.bekasipatriot.activity.ActivityMain.FragmentMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by madeinsap on 6/13/2017.
 */

public class ModelMaps implements ClusterItem {

    private final LatLng mPosition;
    private String name;
    private String alamat;
    private String jam;
    private String telepon;
    private String gambar;
    private String web;
    private double latDes;
    private double lngDes;
    private double latCur;
    private double lngCur;
    private String kategori;
    private String jarak;

    ModelMaps(double lat, double lng, String name, String alamat, String jam, String telepon,
              String gambar, String web, double latDes, double lngDes, double latCur,
              double lngCur, String kategori, String jarak) {
        this.mPosition = new LatLng(lat, lng);
        this.name = name;
        this.alamat = alamat;
        this.jam = jam;
        this.telepon = telepon;
        this.gambar = gambar;
        this.web = web;
        this.latDes = latDes;
        this.lngDes = lngDes;
        this.latCur = latCur;
        this.lngCur = lngCur;
        this.kategori = kategori;
        this.jarak = jarak;
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

    String getJam() {
        return jam;
    }

    String getTelepon() {
        return telepon;
    }

    String getGambar() {
        return gambar;
    }

    String getWeb() {
        return web;
    }

    double getLatDes() {
        return latDes;
    }

    double getLngDes() {
        return lngDes;
    }

    double getLatCur() {
        return latCur;
    }

    double getLngCur() {
        return lngCur;
    }

    String getKategori() {
        return kategori;
    }

    public String getJarak() {
        return jarak;
    }
}
