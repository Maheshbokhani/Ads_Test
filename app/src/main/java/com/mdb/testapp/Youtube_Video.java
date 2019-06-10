package com.mdb.testapp;
import android.content.Intent;
import android.content.res.Configuration;
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
    TextView currenttime,durationtime;
    private YouTubePlayer youTubePlayer;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube__video);



        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyDpqgVkQRgFcSzL57qb5i_nvsgCeo4a_Oc", Youtube_Video.this);



        mHandler =new Handler();


        Play = findViewById(R.id.playButton);


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


        Next = findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Youtube_Video.this, Youtube_Video.class);
//                intent.putExtra("url", xyz);
//                startActivity(intent);
//                finish();
            }
        });


        currenttime = findViewById(R.id.current_time);
        durationtime = findViewById(R.id.play_time);



        FullScreen =findViewById(R.id.fullScreen);
        FullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayer.pause();
                youTubePlayer.setFullscreen(true);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
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


        if (!b) {
            player.loadVideo("fVO_PLQOnWA",0);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            player.setPlayerStateChangeListener(mPlayerStateChangeListener);
            player.setPlaybackEventListener(mPlaybackEventListener);
         }
    }


    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {

        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);

        }

        @Override
        public void onPlaying() {
            mHandler.postDelayed(runnable, 100);
            setcurrenttime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
        }

        @Override
        public void onStopped() {

        }

    };


    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0){   }

        @Override
        public void onLoading() {
            setcurrenttime();
        }

        @Override
        public void onLoaded(String arg0) {
        setcurrenttime();
        }

        @Override
        public void onVideoEnded() {  }

        @Override
        public void onVideoStarted() {
            setcurrenttime();
        }
    };




    private void setcurrenttime() {
         if(null == youTubePlayer) return;
         String text = formatTime(youTubePlayer.getDurationMillis());
         String current =formatTime(youTubePlayer.getCurrentTimeMillis());
         durationtime.setText(text);
         currenttime.setText(current);
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        return (hours == 0 ? "" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
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


