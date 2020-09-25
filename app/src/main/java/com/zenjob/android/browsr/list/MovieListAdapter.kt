package com.zenjob.android.browsr.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.data.Movie


class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback()) {
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onMovieItemClick(
            itemView: View,
            position: Int,
            movie: Movie
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater.inflate(
                R.layout.viewholder_movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, listener)

    }

    class MovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        val titleTv: TextView = itemView.findViewById(R.id.title)
        val ratingTv: TextView = itemView.findViewById(R.id.rating)
        val releaseDateTv: TextView = itemView.findViewById(R.id.release_date)

        fun bind(movie: Movie, listener: OnItemClickListener?) {

            titleTv.text = movie.title
            releaseDateTv.text = android.text.format.DateFormat.format("yyyy", movie.releaseDate)
            ratingTv.text = "${movie.voteAverage ?: 0}"

            itemView.setOnClickListener {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    if (layoutPosition != RecyclerView.NO_POSITION) {
                        listener.onMovieItemClick(itemView, position, movie)
                    }
                }
            }

        }
    }


    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
