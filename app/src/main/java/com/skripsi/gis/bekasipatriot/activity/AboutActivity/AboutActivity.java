package com.skripsi.gis.bekasipatriot.activity.AboutActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.skripsi.gis.bekasipatriot.R;

public class AboutActivity extends AppCompatActivity {
    ImageView fb, twitter, instagram, freepik, eightonesix, quincy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbarMain = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMain);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bekasi Patriot");

        fb = (ImageView) findViewById(R.id.fb);
        fb.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("https://www.facebook.com/madeinsap");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        twitter = (ImageView) findViewById(R.id.twitter);
        twitter.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("https://twitter.com/MadeInSap");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        instagram = (ImageView) findViewById(R.id.instagram);
        instagram.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("http://instagram.com/_u/madeinsap96/");
                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                        likeIng.setPackage("com.instagram.android");
                        try {
                            startActivity(likeIng);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://instagram.com/madeinsap96")));
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        freepik = (ImageView) findViewById(R.id.btnimage1);
        freepik.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("http://www.freepik.com/freepik");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        eightonesix = (ImageView) findViewById(R.id.btnimage2);
        eightonesix.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("http://www.freepik.com/eightonesix");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        quincy = (ImageView) findViewById(R.id.btnimage3);
        quincy.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77eeddff, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();

                        Uri uri = Uri.parse("http://www.freepik.com/quinky");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
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
