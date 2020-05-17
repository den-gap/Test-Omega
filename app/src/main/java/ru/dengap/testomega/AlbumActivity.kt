package ru.dengap.testomega

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import ru.dengap.testomega.ui.albuminfo.AlbumViewModel
import ru.dengap.testomega.ui.albuminfo.SongListAdapter
import java.text.SimpleDateFormat

class AlbumActivity : AppCompatActivity() {

    private val viewModel by viewModels<AlbumViewModel>()
    private lateinit var rvAdapter: SongListAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        if (intent == null && !intent.hasExtra(COLLECTION_ID)) {
            finish()
            return
        }
        toolbar.title = intent.getStringExtra(COLLECTION_NAME)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.loadAlbumInfo(intent.getLongExtra(COLLECTION_ID, 0))
        viewModel.album.observe(this, Observer {
            Picasso.get().load(it.artworkUrl100)
                .placeholder(R.drawable.itunes_placeholder)
                .centerCrop()
                .fit()
                .into(posterIMV)
            with(it) {
                artistNameTV.text = artistName
                collectionNameTV.text = collectionName
                genreTV.text = primaryGenreName
                collectionPriceTV.text = "${collectionPrice?.toInt()}р"
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(releaseDate)
                releaseDateTV.text = SimpleDateFormat("dd.MM.yyyy").format(date)
                copyrightTV.text = copyright
            }

        })
        rvAdapter = SongListAdapter()
        songsRV.apply {
            adapter = rvAdapter
            viewModel.songs.observe(this@AlbumActivity, Observer {
                Log.d("WRITE_TO_ADAPTER", it.size.toString())
                Log.d("WRITE_TO_ADAPTER", it.toString())
                (adapter as SongListAdapter).songList = it
            })
        }
    }

    companion object {
        const val COLLECTION_ID = "collectionId"
        const val COLLECTION_NAME = "collectionName"
        fun newIntent(context: Context, collectionId: Long, collectionName: String): Intent {
            val intent = Intent(context, AlbumActivity::class.java)
            intent.putExtra(COLLECTION_ID, collectionId)
            intent.putExtra(COLLECTION_NAME, collectionName)
            return intent
        }
    }
}
