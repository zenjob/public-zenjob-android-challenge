package com.zenjob.android.browsr.data

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*

data class Movie(
    val id: Long,
    val imdbId: String?,
    val overview: String?,
    val title: String,
    @Json(name = "release_date") val releaseDate: Date?,
    @Json(name = "vote_average") val voteAverage: Float?
) : Serializable
