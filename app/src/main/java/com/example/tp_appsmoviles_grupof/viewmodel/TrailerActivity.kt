package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tp_appsmoviles_grupof.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
// terminar
// import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.PlayerConstants

class TrailerActivity : AppCompatActivity() {

    private lateinit var playerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tráiler"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(playerView)

        val videoId = (intent.getStringExtra("VIDEO_ID") ?: "").trim()
        if (videoId.isEmpty()) {
            Toast.makeText(this, "No se recibió VIDEO_ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val options = IFramePlayerOptions.Builder()
            .controls(1)
            .rel(0)
            .build()

        playerView.enableAutomaticInitialization = false
        playerView.initialize(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {

                youTubePlayer.loadVideo(videoId, 0f)
            }

            // terminar
            /*
            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                if (state == PlayerConstants.PlayerState.ENDED && !isFinishing) finish()
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                abrirEnYouTube(videoId)
            }
            */
        }, options)
    }

    private fun abrirEnYouTube(videoId: String) {

        val url = "https://www.youtube.com/watch?v=$videoId"
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Exception) {
            Toast.makeText(this, "No se pudo abrir YouTube", Toast.LENGTH_SHORT).show()
        } finally {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressedDispatcher.onBackPressed(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        if (::playerView.isInitialized) playerView.release()
        super.onDestroy()
    }
}
