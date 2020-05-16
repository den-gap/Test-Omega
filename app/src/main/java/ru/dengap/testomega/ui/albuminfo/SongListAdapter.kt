package ru.dengap.testomega.ui.albuminfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.song_item.view.*
import ru.dengap.testomega.R
import ru.dengap.testomega.pojo.Track
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SongListAdapter() :
    RecyclerView.Adapter<SongListAdapter.AlbumViewHolder>() {

    var songList: List<Track> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songNumTV = itemView.songNumTV
        val songNameTV = itemView.songNameTV
        val songTimeTV = itemView.songTimeTV
        val songPriceTV = itemView.songPriceTV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.song_item,
            parent,
            false
        )
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.apply {
            songNumTV.text = (position - 1).toString()
            songNameTV.text = songList[position].trackName
            val df: DateFormat = SimpleDateFormat("HH:mm:ss")
            songTimeTV.text = df.format(Date(songList[position].trackTimeMillis!!.toLong()))
            songPriceTV.text = "${songList[position].trackPrice}р."
        }
    }
}