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

    //Изначальный запрос, показывающийся при окрытии приложения
    var query = "jack+johnson"
        set(value) {
            //манипуляция для получения URL encoded текста
            field = value.replace(" ", "+")
            loadAlbums()
        }

    init {
        loadAlbums()
    }

    /**
     * Получаем список альбомов с сервера и заполняем [albums]
     */
    fun loadAlbums() {
        val disposable = ItunesApiFactory.apiService.getAlbums(term = query)
            .map { it.results }
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let { results ->
                    (results as MutableList).map { result ->
                        Log.d("LOADING_ALBUMS_BEFORE", result.artworkUrl100.toString())
                        /*Через API нам присылают изображение, максимальный размер которого 100x100,
                        чего явно нам недостаточно. Я подсмотрел в коде Itunes, что адрес изображения
                        большего размера такой же, разница лишь в названии разрешения, что очень
                        предусмотрительно со стороны Apple. поэтому просто меняем название картинки
                        в адресе ссылки на нее*/
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