package ru.dengap.testomega.ui.albumlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_item.view.*
import ru.dengap.testomega.AlbumActivity
import ru.dengap.testomega.R
import ru.dengap.testomega.pojo.Result

class AlbumListAdapter(private val context: Context) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    var albumList: List<Result> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumImageIMV = itemView.album_imageIMV

        init {
            itemView.setOnClickListener {
                context.startActivity(AlbumActivity.newIntent(context,
                    albumList[adapterPosition].collectionId!!.toLong(),
                    albumList[adapterPosition].collectionName!!))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.album_item,
            parent,
            false
        )
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        Picasso.get().load(albumList[position].artworkUrl100)
            .placeholder(R.drawable.itunes_placeholder)
            .fit()
            .into(holder.albumImageIMV)
    }
}