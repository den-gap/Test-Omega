package ru.dengap.testomega.ui.albuminfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.dengap.testomega.api.ItunesApiFactory
import ru.dengap.testomega.pojo.Album
import ru.dengap.testomega.pojo.Track

class AlbumViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var album: MutableLiveData<Album> = MutableLiveData()
    var songs: MutableLiveData<List<Track>> = MutableLiveData()

    fun loadAlbumInfo(collectionId: Long) {
        Log.d("LOADING_SONGS", collectionId.toString())
        val disposable = ItunesApiFactory.apiService.getAlbumInfo(id = collectionId)
            .map {
                Log.d("LOADING_SONGS", it.results.toString())
                it.results
            }
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let { results ->
                    if (results[0] is Album) {
                        (results[0] as Album).artworkUrl100 = (results[0] as Album).artworkUrl100?.replace("100x100bb", "500x500bb")
                        album.postValue(results[0] as Album)
                    }
                    songs.postValue(results.subList(1, results.size) as List<Track>)
                }
            }, {
                Log.d("LOADING_SONGS", it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}