package com.example.mediaplayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;


public class VideoActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Button btnSelectVideo, btnPlayVideoFromUrl;
    private EditText etVideoUrl;

    private final ActivityResultLauncher<String> selectVideoLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    playVideoFromUri(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playerView = findViewById(R.id.playerView);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnPlayVideoFromUrl = findViewById(R.id.btnPlayVideoFromUrl);
        etVideoUrl = findViewById(R.id.etVideoUrl);

        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        btnSelectVideo.setOnClickListener(v -> selectVideoLauncher.launch("video/*"));

        btnPlayVideoFromUrl.setOnClickListener(v -> {
            String url = etVideoUrl.getText().toString();
            if (!url.isEmpty()) {
                playVideoFromUrl(url);
            }
        });
    }

    private void playVideoFromUri(Uri uri) {
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();
        player.play();
    }

    private void playVideoFromUrl(String url) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(url)));
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}