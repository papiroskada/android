package com.example.mediaplayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class MusicActivity extends AppCompatActivity {

    private Button btnSelectMusic, btnPlayMusic, btnPauseMusic, btnStopMusic, btnPlayFromInternet;
    private TextView tvCurrentTrack, tvStatus;
    private ExoPlayer exoPlayer;
    private String currentTrackName = "No Track Playing";

    private final ActivityResultLauncher<String> selectMusicLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    playMusicFromUri(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        btnSelectMusic = findViewById(R.id.btnSelectMusic);
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);
        btnStopMusic = findViewById(R.id.btnStopMusic);
        btnPlayFromInternet = findViewById(R.id.btnPlayFromInternet);
        tvCurrentTrack = findViewById(R.id.tvCurrentTrack);
        tvStatus = findViewById(R.id.tvStatus);

        exoPlayer = new ExoPlayer.Builder(this).build();

        btnSelectMusic.setOnClickListener(v -> selectMusicLauncher.launch("audio/*"));

        btnPlayMusic.setOnClickListener(v -> {
            if (exoPlayer != null && !exoPlayer.isPlaying()) {
                exoPlayer.play();
                tvStatus.setText("Status: Playing");
            }
        });

        btnPauseMusic.setOnClickListener(v -> {
            if (exoPlayer != null && exoPlayer.isPlaying()) {
                exoPlayer.pause();
                tvStatus.setText("Status: Paused");
            }
        });

        btnStopMusic.setOnClickListener(v -> {
            if (exoPlayer != null) {
                exoPlayer.stop();
                tvStatus.setText("Status: Stopped");
                tvCurrentTrack.setText("No Track Playing");
            }
        });

        btnPlayFromInternet.setOnClickListener(v -> showUrlInputDialog());
    }

    private void playMusicFromUri(Uri uri) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
            MediaItem mediaItem = MediaItem.fromUri(uri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();

            currentTrackName = uri.getLastPathSegment();
            tvCurrentTrack.setText("Now Playing: " + currentTrackName);
            tvStatus.setText("Status: Playing");
        }
    }

    private void playMusicFromInternet(String url) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.clearMediaItems();

            DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                    .setUserAgent("ExoPlayerDemo");

            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(url));

            exoPlayer.setMediaSource(mediaSource);
            exoPlayer.prepare();
            exoPlayer.play();

            currentTrackName = url;
            tvCurrentTrack.setText("Now Streaming: " + currentTrackName);
            tvStatus.setText("Status: Streaming");
        }
    }

    private void showUrlInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Audio URL");

        final EditText input = new EditText(this);
        input.setHint("https://example.com/yourmusic.mp3");
        builder.setView(input);

        builder.setPositiveButton("Play", (dialog, which) -> {
            String url = input.getText().toString().trim();
            if (!url.isEmpty()) {
                playMusicFromInternet(url);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
