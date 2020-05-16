package ru.dengap.testomega

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import ru.dengap.testomega.ui.albuminfo.AlbumViewModel

class AlbumActivity : AppCompatActivity() {

    private val viewModel by viewModels<AlbumViewModel>()

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
        })
        viewModel.songs.observe(this, Observer {

        })
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
