package com.skripsi.gis.bekasipatriot.activity.ActivityListInstansi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Instansi;
import com.skripsi.gis.bekasipatriot.activity.ActivityDetail.DetailActivity;

import java.util.List;

/**
 * Created by madeinsap on 4/16/2017.
 */

public class AdapterInstansi extends RecyclerView.Adapter<AdapterInstansi.ViewHolder> {

    private Context context;
    private List<Instansi> listInstansi;

    public AdapterInstansi(List<Instansi> listInstansi, Context context) {
        super();
        this.listInstansi = listInstansi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_instansi, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Instansi instansi = listInstansi.get(position);
        holder.InstansiName.setText(instansi.getInstansi());
        holder.InstansiJarak.setText(instansi.getJarak());
        Glide.with(context).load("http://" + instansi.getGambar())
                .into(holder.InstansiGambar);

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(new Intent(view.getContext(), DetailActivity.class));
            Instansi item = listInstansi.get(position);
            intent.putExtra("instansi", item.getInstansi());
            intent.putExtra("alamat", item.getAlamat());
            intent.putExtra("gambar", item.getGambar());
            intent.putExtra("jam", item.getJam());
            intent.putExtra("web", item.getWeb());
            intent.putExtra("tlp", item.getTelp());
            intent.putExtra("kategori", item.getKategori());
            intent.putExtra("jarak", item.getJarak());
            intent.putExtra("lat_des", item.getLat());
            intent.putExtra("lng_des", item.getLng());
            intent.putExtra("lat_cur", item.getLatCur());
            intent.putExtra("lng_cur", item.getLngCur());

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listInstansi.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView InstansiName;
        TextView InstansiJarak;
        ImageView InstansiGambar;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            InstansiName = itemView.findViewById(R.id.namainstansi);
            InstansiJarak = itemView.findViewById(R.id.jarakinstansi);
            InstansiGambar = itemView.findViewById(R.id.imagecard);
        }
    }
}
