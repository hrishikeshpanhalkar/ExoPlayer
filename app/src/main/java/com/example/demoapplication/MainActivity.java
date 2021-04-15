package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView imageView,expo_play;
    SimpleExoPlayer exoPlayer;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_View);
        progressBar = findViewById(R.id.progreassbar);
        imageView = findViewById(R.id.bt_fullscreen);
        expo_play = findViewById(R.id.exo_play);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uri videourl = Uri.parse("https://i.imgur.com/7bMqysJ.mp4");
        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector  selector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        exoPlayer = ExoPlayerFactory.newSimpleInstance(MainActivity.this, selector,loadControl);

        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videourl,factory,extractorsFactory,null, null);

        playerView.setPlayer(exoPlayer);

        playerView.setKeepScreenOn(true);

        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                }else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_min));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
            }
        });
    }

    @Override
    public  void onPause(){
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.getPlaybackState();
        expo_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
    }

    @Override
    public  void onRestart(){
        super.onRestart();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.getPlaybackState();
        expo_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }
}