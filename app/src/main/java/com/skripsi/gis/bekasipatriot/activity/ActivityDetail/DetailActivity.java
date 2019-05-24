package com.skripsi.gis.bekasipatriot.activity.ActivityDetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skripsi.gis.bekasipatriot.R;
import com.skripsi.gis.bekasipatriot.activity.ActivityDetail.Adapter.AdapterDetail;
import com.skripsi.gis.bekasipatriot.activity.MyModel.Pelayanan;
import com.skripsi.gis.bekasipatriot.activity.ActivityNavigation.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity{

    TextView instansiAddress, instansiJam, instansiTlp, instansiWeb, instansiJarak;
    String kategori, namaInstansi, alamatInstansi, jamInstansi, tlpInstansi, webInstansi, gambarInstansi, jarakInstansi;
    Button telp, web, navigation;
    double lng_des, lat_des, lng_cur, lat_cur;

    ProgressBar progress;

    List<Pelayanan> GetDataAdapter1 = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://insapol.esy.es/skripsi/data/getKategori.php?kategori=";
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    JSONObject json;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        progress = (ProgressBar) findViewById(R.id.progress);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(namaInstansi);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FFFFFF"));

        telp = findViewById(R.id.btn_call);
        telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tlpInstansi.equals(null) || tlpInstansi.equals("-")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailActivity.this);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setMessage("Nomor telepon tidak tersedia")
                            .setPositiveButton("Kembali",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialogBuilder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                    builder.setMessage("Telpon " + tlpInstansi + " ?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tlpInstansi));
                            try {
                                startActivity(i);
                            } catch (SecurityException se) {
                                Toast.makeText(DetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        web = findViewById(R.id.btn_web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webInstansi.equals(null) || webInstansi.equals("-")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailActivity.this);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setMessage("Halaman web tidak tersedia")
                            .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialogBuilder.show();
                } else {
                    Uri uri = Uri.parse(webInstansi);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        navigation = findViewById(R.id.btn_direction);
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(DetailActivity.this, MapsActivity.class);
                map.putExtra("lat_cur", lat_cur);
                map.putExtra("lng_cur", lng_cur);
                map.putExtra("lat_des", lat_des);
                map.putExtra("lng_des", lng_des);
                map.putExtra("instansi", namaInstansi);
                map.putExtra("alamat", alamatInstansi);
                startActivity(map);
            }
        });

        instansiAddress = (TextView) findViewById(R.id.txt_alamat);
        instansiJam = (TextView) findViewById(R.id.txt_jam);
        instansiWeb = (TextView) findViewById(R.id.txt_web);
        instansiTlp = (TextView) findViewById(R.id.txt_telp);
        instansiJarak = (TextView) findViewById(R.id.txt_jarak);

        instansiAddress.setText(alamatInstansi);
        instansiJam.setText(jamInstansi);
        instansiWeb.setText(webInstansi);
        instansiTlp.setText(tlpInstansi);
        instansiJarak.setText(jarakInstansi);

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerDetail);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new AdapterDetail(GetDataAdapter1, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerView.setNestedScrollingEnabled(false);

        getKategori();

        String url = "http://"+gambarInstansi;
        imgView = (ImageView) findViewById(R.id.imginstansi);
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);
    }

    void getKategori(){
        JSON_DATA(kategori);
    }

    private void JSON_DATA(String kategori) {
        GetDataAdapter1.clear();
        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL + kategori,
                response -> JSON_PARSE_DATA(response),
                error -> {
                    JSON_DATA(kategori);
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Pelayanan GetDataAdapter2 = new Pelayanan();

            json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setPelayanan(json.getString("pelayanan"));
                GetDataAdapter2.setSyarat(json.getString("syarat"));
                GetDataAdapter2.setDetail((json.getString("detail")));
                GetDataAdapter2.setIsidetail((json.getString("isi_detail")));
                GetDataAdapter2.setDetail2((json.getString("detail2")));
                GetDataAdapter2.setIsidetail2((json.getString("isi_detail2")));

                progress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }
        recyclerView.setAdapter(recyclerViewadapter);
    }

    private void getData(){
        Intent i = getIntent();
        lat_des = i.getDoubleExtra("lat_des", 0);
        lng_des = i.getDoubleExtra("lng_des", 0);
        lat_cur = i.getDoubleExtra("lat_cur", 0);
        lng_cur = i.getDoubleExtra("lng_cur", 0);
        namaInstansi = i.getStringExtra("instansi");
        alamatInstansi = i.getStringExtra("alamat");
        gambarInstansi = i.getStringExtra("gambar");
        jamInstansi = i.getStringExtra("jam");
        webInstansi = i.getStringExtra("web");
        tlpInstansi = i.getStringExtra("tlp");
        kategori = i.getStringExtra("kategori");
        jarakInstansi = i.getStringExtra("jarak");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
