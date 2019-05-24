package com.skripsi.gis.bekasipatriot.activity.MyModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madeinsap on 4/16/2017.
 */

public class Instansi {

    @SerializedName("id")
    private String id;
    @SerializedName("instansi")
    private String instansi;
    @SerializedName("jarak")
    private String jarak;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("telp")
    private String telp;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("jam")
    private String jam;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("web")
    private String web;
    @SerializedName("lng")
    private double lng;
    @SerializedName("lat")
    private double lat;

    private double lngCur;
    private double latCur;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getLngCur() {
        return lngCur;
    }

    public void setLngCur(double lngCur) {
        this.lngCur = lngCur;
    }

    public double getLatCur() {
        return latCur;
    }

    public void setLatCur(double latCur) {
        this.latCur = latCur;
    }
}
