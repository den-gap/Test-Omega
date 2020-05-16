package ru.dengap.testomega

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.dengap.testomega.ui.albumlist.AlbumListAdapter
import ru.dengap.testomega.ui.albumlist.AlbumListViewModel

class MainActivity : AppCompatActivity() {

    private var columnCount = 2
    private lateinit var rvAdapter: AlbumListAdapter
    private val viewModel by viewModels<AlbumListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvAdapter = AlbumListAdapter(this)
        album_listRV.apply {
            adapter = rvAdapter
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            viewModel.albums.observe(this@MainActivity, Observer {
                Log.d("WRITE_TO_ADAPTER", it.size.toString())
                Log.d("WRITE_TO_ADAPTER", it.toString())
                (adapter as AlbumListAdapter).albumList = it
            })
        }
    }
}
