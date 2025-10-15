
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp_appsmoviles_grupof.databinding.ItemMovieBinding


class MovieAdapter(
    private var movies: List<Movie>,
    private val onBuyClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        with(holder.binding) {
            titleText.text = movie.title
            Glide.with(root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(posterImage)

            buyButton.setOnClickListener { onBuyClick(movie) }
        }
    }
}
