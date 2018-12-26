package com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.Model;

/**
 * Created by madeinsap on 4/16/2017.
 */

public class ModelCard {
    private String id, instansi, jarak, gambar, telp, alamat, jam, kategori, web;
    private double lng, lat, lat_cur, lng_cur;

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

    public double getLng_cur() {
        return lng_cur;
    }

    public void setLng_cur(double lng_cur) {
        this.lng_cur = lng_cur;
    }

    public double getLat_cur() {
        return lat_cur;
    }

    public void setLat_cur(double lat_cur) {
        this.lat_cur = lat_cur;
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
}
