package com.wemeal.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.wemeal.R

/**
 * Created by Hani AlMomani on 24,April,2019
 */

class GalleryAdapter(var clickListener: (position : Int) -> Unit) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val diffResult = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_gallery,
                parent,
                false
            )
        )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return diffResult.currentList.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener.invoke(position) }
        val item = diffResult.currentList[position]


        Glide.with(holder.img).load(item)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .thumbnail(0.5f)
//            .centerCrop()
            .into(holder.img)
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: AppCompatImageView = itemView.findViewById(R.id.gallery_image)
        val overlay: View = itemView.findViewById(R.id.overlay)
    }

}