package com.example.technews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val items: ArrayList<News>,
                  private val listener: OnItemClickListener
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        if(currentItem.author != "null"){
            holder.author.text = currentItem.author
        } else{
            holder.author.text = "anonymous"
        }

        Glide.with(holder.itemView.context).load(currentItem.imageURL).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val author: TextView = itemView.findViewById(R.id.authorName)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}





