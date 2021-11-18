package com.test.flicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.test.flicker.R
import com.test.flicker.databinding.FragmentPhotoZoomInZoomOutBinding
import com.test.flicker.model.Photo
import com.test.flicker.util.AppUtils.getPhotoUrl
import com.test.flicker.util.AppUtils.setImageFromUrl
import com.test.flicker.util.PhotoSizeType


class PhotoZoomInZoomOutFragment(private val photo: Photo) : DialogFragment() {

    private lateinit var binding: FragmentPhotoZoomInZoomOutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_photo_zoom_in_zoom_out, container, false)
        binding.photoModel = photo
        setImageFromUrl(binding.izvPhoto, getPhotoUrl(photo, PhotoSizeType.z.name), binding.pbPhotoProgress)
        return binding.root
    }
}