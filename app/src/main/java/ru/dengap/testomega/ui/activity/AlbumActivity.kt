package ru.dengap.testomega.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import ru.dengap.testomega.R
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
        //загрудаем информацию об альбоме по id, переданного в активити
        viewModel.loadAlbumInfo(intent.getLongExtra(COLLECTION_ID, 0))
        //Отображаем данные в нужные поля для отображения информации о выбранном альбоме
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
        //Передаем в адаптер треки
        songsRV.apply {
            adapter = rvAdapter
            viewModel.songs.observe(this@AlbumActivity, Observer {
                Log.d("WRITE_TO_ADAPTER", it.size.toString())
                Log.d("WRITE_TO_ADAPTER", it.toString())
                (adapter as SongListAdapter).songList = it
                //если все ок, то скрываем прогресс бар и показываем информацию
                allInfoGroup.visibility = View.VISIBLE
                progressBar.visibility = ProgressBar.INVISIBLE
            })
        }
    }


    companion object {
        const val COLLECTION_ID = "collectionId"
        const val COLLECTION_NAME = "collectionName"

        /* Функция для вызова этой активити, благодаря которой мы знаем, какие данные понадобятся
        для ее запуска и работы, и не запутаемся */
        fun newIntent(context: Context, collectionId: Long, collectionName: String): Intent {
            val intent = Intent(context, AlbumActivity::class.java)
            intent.putExtra(COLLECTION_ID, collectionId)
            intent.putExtra(COLLECTION_NAME, collectionName)
            return intent
        }
    }
}
