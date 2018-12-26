package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentHome;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.skripsi.gis.bekasipatriot.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    private List<Slider> sliderList;

    public SliderAdapter(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Slider slider = sliderList.get(position % sliderList.size());
        Glide.with(holder.imageSlider.getContext()).load(slider.getImageSlider()).into(holder.imageSlider);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getUrlWeb()));
                v.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageSlider;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageSlider = itemView.findViewById(R.id.imageSlider);
        }
    }

}
