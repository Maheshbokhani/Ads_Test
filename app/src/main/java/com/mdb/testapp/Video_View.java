package com.mdb.testapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Video_View extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener,SimpleGestureFilter.SimpleGestureListener {

    public static final String API_KEY = "AIzaSyDpqgVkQRgFcSzL57qb5i_nvsgCeo4a_Oc";

    private SimpleGestureFilter detector;

    private YouTubePlayer mPlayer;
    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView, current_time;
    private Handler handler = new Handler();
    private int progressStatus = 0;
    private Handler mHandler = null;
    private SeekBar mSeekBar;
    ImageButton imageButton, play_button,SeekTime,SeekTimeReverse;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__view);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }



        detector = new SimpleGestureFilter(Video_View.this, this);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(API_KEY, this);

        mPlayButtonLayout = findViewById(R.id.video_control);

              play_button =  findViewById(R.id.play_video);
              play_button.setOnClickListener(this);
              play_button.setBackgroundResource(R.drawable.play);


            mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
            current_time = (TextView) findViewById(R.id.current_time);
            mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
            mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);

            mHandler = new Handler();


            imageButton = findViewById(R.id.fullScreen);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayer.pause();
                    mPlayer.setFullscreen(true);
                    mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                }
            });


        SeekTime = findViewById(R.id.seekvideo);
        SeekTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.seekRelativeMillis(10000);
            }
        });
        SeekTimeReverse = findViewById(R.id.seekvideoreverse);
        SeekTimeReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.seekRelativeMillis(-10000);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }


    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "You have Double Tapped.", Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
        if (null == player) return;
        mPlayer = player;


        displayCurrentTime();
        if (null == mPlayer) return;
        String formattedTime = formatTime(player.getDurationMillis() - player.getCurrentTimeMillis());
        mPlayTimeTextView.setText(formattedTime);


        Intent intent = getIntent();
        String video = intent.getStringExtra("url");

        // Start buffering
        if (!b) {
            player.loadVideo(video);
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }



        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
        player.setPlayerStateChangeListener(mPlayerStateChangeListener);
        player.setPlaybackEventListener(mPlaybackEventListener);
    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
            onSeekTo(1000);
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
           play_button.setBackgroundResource(R.drawable.play);

        }

        @Override
        public void onPlaying() {
            play_button.setBackgroundResource(R.drawable.pause);
            mHandler.postDelayed(runnable, 100);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }

    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onVideoEnded() {
            Intent intent = new Intent(Video_View.this, Video_View.class);
            intent.putExtra("url", "XX6FWguCp2s");
            startActivity(intent);
            finish();
        }

        @Override
        public void onVideoStarted() {
            displayCurrentTime();
        }
    };

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
//
           final long lengthPlayed = (mPlayer.getDurationMillis() * progress) / 100;
            mPlayer.seekToMillis((int) lengthPlayed);
             mSeekBar.setProgress((int)lengthPlayed);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_video:
                if (null != mPlayer && !mPlayer.isPlaying()) {
                    mPlayer.play();
                } else if (null != mPlayer && mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
                break;

        }
    }

    private void displayCurrentTime() {
        if (null == mPlayer) return;
        String formattedTime = formatTime(mPlayer.getDurationMillis());
        mPlayTimeTextView.setText(formattedTime);
        String Current = formatTime(mPlayer.getCurrentTimeMillis());
        current_time.setText(Current);
    }

    private String formatTime(int i) {
        int seconds = i / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        return (hours == 0 ? "" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            displayCurrentTime();
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                startActivity(new Intent(this, B_Hits.class));
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                Intent intent = new Intent(Video_View.this, Video_View.class);
                intent.putExtra("url","6Zb82nFw3D0");
                startActivity(intent);
                finish();

            case SimpleGestureFilter.SWIPE_DOWN:
                startActivity(new Intent(Video_View.this, MainActivity.class));
                finish();
            case SimpleGestureFilter.SWIPE_UP:
                finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}