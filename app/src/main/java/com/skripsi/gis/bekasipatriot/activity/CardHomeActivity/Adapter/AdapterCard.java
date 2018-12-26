package com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.CardHomeActivity.Model.ModelCard;
import com.skripsi.gis.bekasipatriot.activity.DetailActivity.DetailActivity;

import java.util.List;

/**
 * Created by madeinsap on 4/16/2017.
 */

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {

    Context context;
    List<ModelCard> getDataAdapter;

    public AdapterCard(List<ModelCard> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_instansi, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ModelCard getDataAdapter1 =  getDataAdapter.get(position);
        holder.InstansiName.setText(getDataAdapter1.getInstansi());
        holder.InstansiJarak.setText(getDataAdapter1.getJarak()+" km");
        Glide.with(context).load("http://" + getDataAdapter1.getGambar())
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.InstansiGambar);

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(new Intent(v.getContext(),DetailActivity.class));
                ModelCard item = getDataAdapter.get(position);
                if(item.getInstansi().equals(item.getInstansi())){
                    intent.putExtra("instansi", item.getInstansi());
                    intent.putExtra("alamat", item.getAlamat());
                    intent.putExtra("gambar", item.getGambar());
                    intent.putExtra("jam", item.getJam());
                    intent.putExtra("web", item.getWeb());
                    intent.putExtra("tlp", item.getTelp());
                    intent.putExtra("kategori", item.getKategori());
                    intent.putExtra("jarak", item.getJarak());
                    intent.putExtra("lat_cur", item.getLat_cur());
                    intent.putExtra("lng_cur", item.getLng_cur());
                    intent.putExtra("lat_des", item.getLat());
                    intent.putExtra("lng_des", item.getLng());

                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView InstansiName;
        public TextView InstansiJarak;
        public ImageView InstansiGambar;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            InstansiName = (TextView) itemView.findViewById(R.id.namainstansi);
            InstansiJarak = (TextView) itemView.findViewById(R.id.jarakinstansi);
            InstansiGambar = (ImageView) itemView.findViewById(R.id.imagecard);
        }
    }
}
