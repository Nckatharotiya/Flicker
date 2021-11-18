package com.test.flicker.util;

import static com.test.flicker.util.AppConstant.PHOTO_BASE_URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.flicker.model.Photo;

public class AppUtils {

    public static String getPhotoUrl(Photo photo, String size){
        return PHOTO_BASE_URL +photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+"_"+size+".jpg";
    }

    public static void hideKeyboard(View view) {
        if(view == null)
            return;
        InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setImageFromUrl(ImageView imageView, String imageUrl, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(imageUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        imageView.setImageBitmap(resource);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerInside()
                .dontAnimate()
                .into(imageView);
    }
}
