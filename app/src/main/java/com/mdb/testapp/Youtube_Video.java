package com.mdb.testapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube_Video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubeView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    ImageButton Play, Next, FullScreen,SeekTime,SeekTimeReverse;
    String xyz = "fVO_PLQOnWA";
    String abc;
    TextView textView;
    private YouTubePlayer youTubePlayer;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube__video);



        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyDpqgVkQRgFcSzL57qb5i_nvsgCeo4a_Oc", Youtube_Video.this);

        mHandler =new Handler();

        textView =findViewById(R.id.videoSize);


        Play = findViewById(R.id.playButton);
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              youTubePlayer.pause();

            }
        });

        FullScreen =findViewById(R.id.fullScreen);
        FullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayer.setFullscreen(true);

            }
        });

        SeekTime = findViewById(R.id.seekvideo);
        SeekTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayer.seekRelativeMillis(10000);
            }
        });
        SeekTimeReverse = findViewById(R.id.seekvideoreverse);
        SeekTimeReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayer.seekRelativeMillis(-10000);
            }
        });


//        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider,
//                                                YouTubePlayer player, boolean b) {
//                youTubePlayer = player;
//
//                Intent intent = getIntent();
//                abc = intent.getStringExtra("url");
//
//                //  youTubePlayer.loadVideo("3XBHBOhGMe4", 0);
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//                if (!b) {
//                    youTubePlayer.loadVideo(abc, 0);
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                                YouTubeInitializationResult youTubeInitializationResult) {
//            }
//        };


        Next = findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Youtube_Video.this, Youtube_Video.class);
                intent.putExtra("url", xyz);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean b) {
        youTubePlayer = player;
        Intent intent = getIntent();
        abc = intent.getStringExtra("url");

        setcurrenttime();

        //  youTubePlayer.loadVideo("3XBHBOhGMe4", 0);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);



        if (!b) {
            player.loadVideo(abc,0);
        }
    }

    private void setcurrenttime() {
         if(null == youTubePlayer) return;
         String text = formatTime(youTubePlayer.getDurationMillis());
         textView.setText(text);
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setcurrenttime();
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
    }

}


