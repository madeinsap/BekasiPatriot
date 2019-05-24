package com.skripsi.gis.bekasipatriot.activity.ActivityDetail.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Pelayanan;

import java.util.List;

/**
 * Created by madeinsap on 4/25/2017.
 */

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.ViewHolder> {

    Context context;
    List<Pelayanan> getDataAdapter;

    public AdapterDetail(List<Pelayanan> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public AdapterDetail.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        AdapterDetail.ViewHolder viewHolder = new AdapterDetail.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterDetail.ViewHolder holder, final int position) {
        Pelayanan getDataAdapter1 =  getDataAdapter.get(position);
        holder.InstansiPelayanan.setText(getDataAdapter1.getPelayanan());
        holder.InstansiSyarat.setText(getDataAdapter1.getSyarat());
        holder.InstansiDetail.setText(getDataAdapter1.getDetail());
        holder.InstansiIsidetail.setText(getDataAdapter1.getIsidetail());
        holder.InstansiDetail2.setText(getDataAdapter1.getDetail2());
        holder.InstansiIsidetail2.setText(getDataAdapter1.getIsidetail2());
    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView InstansiPelayanan;
        public TextView InstansiSyarat;
        public TextView InstansiDetail;
        public TextView InstansiIsidetail;
        public TextView InstansiDetail2;
        public TextView InstansiIsidetail2;

        public ViewHolder(View itemView) {
            super(itemView);
            InstansiPelayanan = (TextView) itemView.findViewById(R.id.txt_pelayanan);
            InstansiSyarat = (TextView) itemView.findViewById(R.id.textSyarat);
            InstansiDetail = (TextView) itemView.findViewById(R.id.detail);
            InstansiIsidetail = (TextView) itemView.findViewById(R.id.isiDetail);
            InstansiDetail2 = (TextView) itemView.findViewById(R.id.detail_info);
            InstansiIsidetail2 = (TextView) itemView.findViewById(R.id.isiDetail2);
        }
    }
}
