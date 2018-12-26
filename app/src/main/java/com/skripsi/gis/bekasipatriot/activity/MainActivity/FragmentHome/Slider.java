package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentHome;

public class Slider {
    private String imageSlider;
    private String urlWeb;

    public Slider() {

    }

    public Slider(String imageSlider, String urlWeb) {
        this.imageSlider = imageSlider;
        this.urlWeb = urlWeb;
    }

    public String getImageSlider() {
        return imageSlider;
    }

    public void setImageSlider(String imageSlider) {
        this.imageSlider = imageSlider;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }
}
