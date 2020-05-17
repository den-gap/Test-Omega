package ru.dengap.testomega.ui.albuminfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.dengap.testomega.api.ItunesApiFactory
import ru.dengap.testomega.pojo.Result

class AlbumViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //информация о выбранном альбоме
    var album: MutableLiveData<Result> = MutableLiveData()

    //информация о треках выбранного альбома
    var songs: MutableLiveData<List<Result>> = MutableLiveData()

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
                    results[0].artworkUrl100 =
                        results[0].artworkUrl100?.replace("100x100bb", "500x500bb")
                    //первый элемент при подобном запросе у нас всегда типа collection
                    album.postValue(results[0])
                    //остальное треки
                    songs.postValue(results.subList(1, results.size))
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