package com.test.flicker.di;

import static com.test.flicker.util.AppConstant.SERVER_BASE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.flicker.repository.PhotosService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    PhotosService providePhotosService(Retrofit retrofit) {
        return retrofit.create(PhotosService.class);
    }
}
