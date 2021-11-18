package com.test.flicker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.flicker.R
import com.test.flicker.databinding.RowPhotoBinding
import com.test.flicker.model.Photo
import com.test.flicker.util.AppUtils.getPhotoUrl
import com.test.flicker.util.AppUtils.setImageFromUrl
import com.test.flicker.util.PhotoSizeType
import io.reactivex.rxjava3.functions.Consumer

class PhotosAdapter(private val consumer: Consumer<Photo>, val photos: MutableList<Photo> = mutableListOf()) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val binding: RowPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_photo, parent, false)
        return PhotosViewHolder(binding)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.binding.photoModel = photos[position]
        setImageFromUrl(holder.binding.ivThumbnail, getPhotoUrl(photos[position], PhotoSizeType.q.name), holder.binding.pbPhotoProgress)
        holder.binding.root.setOnClickListener {
            consumer.accept(photos[position])
        }
    }

    class PhotosViewHolder(val binding: RowPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)
}