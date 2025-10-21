package com.example.tp_appsmoviles_grupof.viewmodel.Peliculas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tp_appsmoviles_grupof.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailerFragment : Fragment() {
    private var videoId: String? = null
    private var playerView: YouTubePlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoId = arguments?.getString(ARG_VIDEO_ID)?.trim()
        if (videoId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Sin VIDEO_ID", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_trailer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.btnClose).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        playerView = view.findViewById(R.id.youtube_player_view)
        viewLifecycleOwner.lifecycle.addObserver(playerView!!)

        val options = IFramePlayerOptions.Builder()
            .controls(1)
            .rel(0)
            .build()

        val key = videoId ?: return
        playerView?.enableAutomaticInitialization = false
        playerView?.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                try {
                    youTubePlayer.loadVideo(key, 0f)
                } catch (e: Exception) {
                    // Fallback si el embed falla
                    abrirEnYouTube(key)
                }
            }
        }, options)
    }

    private fun abrirEnYouTube(key: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$key")))
        } catch (_: Exception) {
            Toast.makeText(requireContext(), "No se pudo abrir YouTube", Toast.LENGTH_SHORT).show()
        } finally {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        playerView?.release()
        playerView = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_VIDEO_ID = "VIDEO_ID"
        fun newInstance(videoId: String) = TrailerFragment().apply {
            arguments = Bundle().apply { putString(ARG_VIDEO_ID, videoId) }
        }
    }
}
