package com.mdb.testapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube_Video extends YouTubeBaseActivity {

    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube__video);


        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        youTubeView.initialize("AIzaSyDpqgVkQRgFcSzL57qb5i_nvsgCeo4a_Oc", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
             youTubePlayer.loadVideo("3XBHBOhGMe4",0);
             youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);


            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


    }
}
