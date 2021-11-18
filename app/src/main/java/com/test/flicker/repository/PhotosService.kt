package com.test.flicker.repository

import com.test.flicker.model.PhotosDataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotosService {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&per_page=25")
    fun getPhotoSearchResult(@Query("api_key") apiKey: String,
                             @Query("text") searchText: String): Observable<PhotosDataModel>
}