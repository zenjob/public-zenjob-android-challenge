package com.zenjob.android.browsr.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zenjob.android.browsr.BuildConfig
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.data.DateJsonAdapter
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.data.TMDBApi
import com.zenjob.android.browsr.detail.DetailActivity
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class ListActivity : AppCompatActivity(), MovieListAdapter.OnItemClickListener {

    val mAdapter = MovieListAdapter().apply { listener = this@ListActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val list: RecyclerView = findViewById(R.id.list)

        list.adapter = mAdapter

        fetchMovies()

        val refresh = findViewById<View>(R.id.refresh)
        refresh.setOnClickListener {
            fetchMovies()
        }
    }

    override fun onMovieItemClick(
        itemView: View,
        position: Int,
        movie: Movie
    ) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    fun fetchMovies() {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateJsonAdapter())
            .build()

        val tmdbApiInterceptor = Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            val reqBuilder = original.newBuilder()
                .url(url)
            chain.proceed(reqBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(tmdbApiInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        retrofit.create(TMDBApi::class.java).getPopularTvShows()
            .subscribeOn(Schedulers.io())
            .subscribe { paginatedList ->
                mAdapter.submitList(paginatedList.results)
            }
    }

}
