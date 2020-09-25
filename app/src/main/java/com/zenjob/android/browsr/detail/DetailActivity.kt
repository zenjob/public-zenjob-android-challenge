package com.zenjob.android.browsr.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.data.Movie


class DetailActivity : AppCompatActivity() {

    val titleTv: TextView by lazy { findViewById<TextView>(R.id.title) }
    val ratingTv: TextView by lazy { findViewById<TextView>(R.id.rating) }
    val releaseDateTv: TextView by lazy { findViewById<TextView>(R.id.release_date) }
    val descriptionTv: TextView by lazy { findViewById<TextView>(R.id.description) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = if (intent.hasExtra("movie")) intent.getSerializableExtra("movie") as Movie else null

        if(movie == null) return

        titleTv.text = movie.title
        releaseDateTv.text =
            android.text.format.DateFormat.format("yyyy", movie.releaseDate)
        ratingTv.text = "${movie.voteAverage ?: 0}"
        descriptionTv.text = movie.overview
    }

}
