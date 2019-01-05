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

    public ModelMaps(double lat, double lng, String name, String alamat, String jam, String telepon,
                     String gambar, String web, double latDes, double lngDes, double latCur,
                     double lngCur, String kategori, String jarak) {
        this.mPosition = new LatLng(lat, lng);;
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

    public String getJam() {
        return jam;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getGambar() {
        return gambar;
    }

    public String getWeb() {
        return web;
    }

    public double getLatDes() {
        return latDes;
    }

    public double getLngDes() {
        return lngDes;
    }

    public double getLatCur() {
        return latCur;
    }

    public double getLngCur() {
        return lngCur;
    }

    public String getKategori() {
        return kategori;
    }

    public String getJarak() {
        return jarak;
    }
}
