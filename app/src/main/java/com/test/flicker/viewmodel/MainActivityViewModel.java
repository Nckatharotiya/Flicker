package com.test.flicker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.flicker.model.PhotosDataModel;
import com.test.flicker.repository.PhotosRepository;
import com.test.flicker.util.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {
    private final PhotosRepository photosRepository;

    private MutableLiveData<Resource<PhotosDataModel>> searchPhotosMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<PhotosDataModel>> searchPhotosLiveData = searchPhotosMutableLiveData;
    @Inject
    public MainActivityViewModel(PhotosRepository photosRepository){
        this.photosRepository = photosRepository;
    }

    public void searchPhotos(String searchText){

        photosRepository.searchPhotos(searchText)
                .doOnSubscribe(disposable -> searchPhotosMutableLiveData.postValue(Resource.loading(null)))
                .subscribe(value -> searchPhotosMutableLiveData.postValue(Resource.success(value)),
                        error -> searchPhotosMutableLiveData.postValue(Resource.error(error)));
    }

    public LiveData<Resource<PhotosDataModel>> getSearchPhotosLiveData() {
        return searchPhotosLiveData;
    }
}
