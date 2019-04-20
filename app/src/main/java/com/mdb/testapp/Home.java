package com.mdb.testapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements View.OnClickListener {

    ImageView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!isConnected(Home.this))
            buildDialog(Home.this).show();
        else {

        }

        a1 = findViewById(R.id.a1);
        a1.setOnClickListener(this);
        a2 = findViewById(R.id.a2);
        a2.setOnClickListener(this);
        a3 = findViewById(R.id.a3);
        a3.setOnClickListener(this);
        a4 = findViewById(R.id.a4);
        a4.setOnClickListener(this);
        a5 = findViewById(R.id.a5);
        a5.setOnClickListener(this);
        a6 = findViewById(R.id.a6);
        a6.setOnClickListener(this);
        a7 = findViewById(R.id.a7);
        a7.setOnClickListener(this);
        a8 = findViewById(R.id.a8);
        a8.setOnClickListener(this);
        a9 = findViewById(R.id.a9);
        a9.setOnClickListener(this);
        a10 = findViewById(R.id.a10);
        a10.setOnClickListener(this);
        a11 = findViewById(R.id.a11);
        a11.setOnClickListener(this);
        a12 = findViewById(R.id.a12);
        a12.setOnClickListener(this);


        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/PD-QpAPBKgk/sddefault.jpg").fit().into(a1);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/64UX1npgRkI/sddefault.jpg").fit().into(a2);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/AvgFmr-ckpk/sddefault.jpg").fit().into(a3);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/3SWc5G8Gx7E/hqdefault.jpg").fit().into(a4);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/G5wC5ze8ySQ/sddefault.jpg").fit().into(a5);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/Skp5roPkjys/sddefault.jpg").fit().into(a6);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/OX-h7MtkeOI/sddefault.jpg").fit().into(a7);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/BUobY0YtF48/sddefault.jpg").fit().into(a8);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/d8O6SoPOkAk/sddefault.jpg").fit().into(a9);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/d8O6SoPOkAk/sddefault.jpg").fit().into(a10);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/f1qz8vn3XbY/sddefault.jpg").fit().into(a11);
        Picasso.with(getBaseContext()).load("https://i.ytimg.com/vi/aNwWdF8qq-M/sddefault.jpg").fit().into(a12);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.a1:

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && networkInfo.isConnectedOrConnecting()) || (wifi != null && networkInfo.isConnectedOrConnecting())) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context c){
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setCancelable(false);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your INTERNET or Wi-Fi connection.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }
}

