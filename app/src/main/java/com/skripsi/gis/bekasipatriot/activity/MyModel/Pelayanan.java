package com.skripsi.gis.bekasipatriot.activity.MyModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madeinsap on 4/25/2017.
 */

public class Pelayanan {

    @SerializedName("kategori")
    private String kategori;
    @SerializedName("pelayanan")
    private String pelayanan;
    @SerializedName("syarat")
    private String syarat;
    @SerializedName("detail")
    private String detail;
    @SerializedName("isi_detail")
    private String isidetail;
    @SerializedName("detail2")
    private String detail2;
    @SerializedName("isi_detail2")
    private String isidetail2;

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(String pelayanan) {
        this.pelayanan = pelayanan;
    }

    public String getSyarat() {
        return syarat;
    }

    public void setSyarat(String syarat) {
        this.syarat = syarat;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIsidetail() {
        return isidetail;
    }

    public void setIsidetail(String isidetail) {
        this.isidetail = isidetail;
    }

    public String getDetail2() {
        return detail2;
    }

    public void setDetail2(String detail2) {
        this.detail2 = detail2;
    }

    public String getIsidetail2() {
        return isidetail2;
    }

    public void setIsidetail2(String isidetail2) {
        this.isidetail2 = isidetail2;
    }
}
