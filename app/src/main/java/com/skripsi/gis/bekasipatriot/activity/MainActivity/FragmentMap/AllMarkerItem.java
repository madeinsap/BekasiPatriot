package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentMap;

public class AllMarkerItem {
    String instansi;
    String alamat;
    String jarak;
    String telepon;
    String gambar;

    public AllMarkerItem(String instansi, String alamat, String jarak, String telepon, String gambar) {
        this.instansi = instansi;
        this.alamat = alamat;
        this.jarak = jarak;
        this.telepon = telepon;
        this.gambar = gambar;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
