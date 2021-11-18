package com.test.flicker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.flicker.model.PhotosDataModel
import com.test.flicker.util.AppConstant.API_KEY
import com.test.flicker.util.Resource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class PhotosRepository @Inject constructor(private val photosService: PhotosService) {

    fun searchPhotos(searchText:String): Observable<PhotosDataModel> {
        return photosService.getPhotoSearchResult(API_KEY, searchText)
    }
}