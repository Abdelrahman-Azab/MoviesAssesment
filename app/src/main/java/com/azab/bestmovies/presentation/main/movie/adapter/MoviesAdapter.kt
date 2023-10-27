package com.azab.bestmovies.presentation.main.movie.adapter

import com.azab.bestmovies.data.remote.model.Movie
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azab.bestmovies.R
import com.azab.bestmovies.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MoviesAdapter(private val onMovieClickListener: SetMovieClickListener
) : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieDiffUtil) {


    object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie,onMovieClickListener)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(movie: Movie, onMovieClickListener: SetMovieClickListener) {
            binding.apply {
                binding.movieNameTv.text = movie.title
                binding.movieIdTv.text = binding.root.context.getString(R.string.id) + ":" + movie.imdbID
                binding.movieYearTv.text = binding.root.context.getString(R.string.year) + ":" + movie.year
                Glide.with(root.context).load(movie.poster).into(binding.moviePosterIv)
                binding.likeDislikeIv.setOnClickListener {
                    movie.liked = !(movie.liked?:false)
                    onMovieClickListener.onMovieClicked(movie,bindingAdapterPosition)
                    notifyItemChanged(absoluteAdapterPosition)
                }
                if (movie.liked == true)
                    binding.likeDislikeIv.setImageDrawable(binding.root.context.getDrawable(R.drawable.ic_like))
                else
                    binding.likeDislikeIv.setImageDrawable(binding.root.context.getDrawable(R.drawable.ic_dislike))

            }
        }
    }


}
interface SetMovieClickListener {
    fun onMovieClicked(item: Movie, position: Int)

}