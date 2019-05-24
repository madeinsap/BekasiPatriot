package com.skripsi.gis.bekasipatriot.activity.ActivityMain.FragmentHome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Slider;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context context;
    List<Slider> listSlider;

    public SliderAdapter(Context context, List<Slider> listSlider) {
        this.context = context;
        this.listSlider = listSlider;
    }

    @Override
    public int getCount() {
        return listSlider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_home, container, false);
        ImageView imageSlider = view.findViewById(R.id.imageSlider);

        Glide.with(imageSlider.getContext()).load(listSlider.get(position).getImageSlider()).into(imageSlider);

        imageSlider.setOnClickListener(v -> {
            Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listSlider.get(position).getUrlWeb()));
            v.getContext().startActivity(mIntent);
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
