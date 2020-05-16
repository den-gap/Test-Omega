package ru.dengap.testomega.ui.albumlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.dengap.testomega.api.ItunesApiFactory
import ru.dengap.testomega.pojo.Result

class AlbumListViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var albums: MutableLiveData<List<Result>> = MutableLiveData()

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        val disposable = ItunesApiFactory.apiService.getAlbums(term = "jack+johnson")
            .map { it.results }
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let { results ->
                    (results as MutableList).map { result ->
                        Log.d("LOADING_ALBUMS_BEFORE", result.artworkUrl100.toString())
                        result.artworkUrl100 =
                            result.artworkUrl100?.replace("100x100bb", "500x500bb")
                        Log.d("LOADING_ALBUMS_AFTER", result.artworkUrl100.toString())

                    }
                    albums.postValue(results as List<Result>)
                }
            }, {
                Log.d("LOADING_ALBUMS", it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}