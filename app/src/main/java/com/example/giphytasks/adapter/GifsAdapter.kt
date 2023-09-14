package com.example.giphytasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphytasks.R
import com.example.giphytasks.const.Layout
import com.example.giphytasks.data.DataObject

class GifsAdapter(private val context: Context, private val gifs: List<DataObject>, private val layout: Int)
    : RecyclerView.Adapter<GifsAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutResId = when (layout) {
            Layout.GRID -> R.layout.grid_list_item
            else -> R.layout.vertical_horizontal_list_item
        }

        return ViewHolder(LayoutInflater.from(context).inflate(layoutResId, parent, false), mListener)
    }

    override fun getItemCount() = gifs.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.images.ogImage.url)
            .into(holder.imageView)
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.item_image)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}