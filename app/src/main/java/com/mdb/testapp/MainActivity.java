package com.mdb.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.durranilab.labprogresslayout.LabProgressLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity implements View.OnClickListener {

    private AdView mAdView;
    Button  Youtube,Test,BHits;
    LabProgressLayout labProgressLayout;
    int start =0 ;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        labProgressLayout = findViewById(R.id.labProgressLayout);

        Youtube = (Button) findViewById(R.id.youtube_video);
        Test = (Button) findViewById(R.id.test);

        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Youtube_Video.class));
            }
        });

        Youtube.setOnClickListener(this);

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        BHits = (Button) findViewById(R.id.b_hits);

        BHits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, B_Hits.class));
            }
        });


        new Thread(new Runnable() {
       @Override
       public void run() {
           while(start<=2000){
               start+=1;
           }
           try{
               Thread.sleep(10);
           }catch(InterruptedException e){
               e.printStackTrace();
           }

           //Update the progress bar
           handler.post(new Runnable() {
               public void run() {
                   labProgressLayout.setCurrentProgress(start);
               }
           });
       }
   }).start();


    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.youtube_video:
                Intent intent = new Intent(getBaseContext(), Video_View.class);
                intent.putExtra("url","ttCUfDtrYlU");
                startActivity(intent);
        }

    }

}