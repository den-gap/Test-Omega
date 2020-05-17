package ru.dengap.testomega.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.dengap.testomega.R
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
        //Используем котлин синтетик для поиска view
        album_listRV.apply {
            adapter = rvAdapter
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            //Подписываемся на изменения альбомов для передачи их в адаптер RecyclerView
            viewModel.albums.observe(this@MainActivity, Observer {
                Log.d("WRITE_TO_ADAPTER", it.size.toString())
                Log.d("WRITE_TO_ADAPTER", it.toString())
                (adapter as AlbumListAdapter).albumList = it
            })
        }
    }

    /**
     * Здесь мы только подключаем поиск в стандарный ActionVar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_album_list, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.apply {
            maxWidth = Int.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.query = query
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
        return true
    }
}
