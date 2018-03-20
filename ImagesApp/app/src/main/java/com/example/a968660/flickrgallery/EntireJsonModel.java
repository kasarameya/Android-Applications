package com.example.a968660.flickrgallery;

import com.google.gson.annotations.SerializedName;

/**
 * FlickrGallery
 * <p>
 *
 * <br/>
 * <b>-------Class Description-------------------</b>
 * <br/>
 */

public class EntireJsonModel {
    @SerializedName("photos")
    private PhotosJsonBean mPhotosJsonBean;

    public PhotosJsonBean getPhotosJsonBean() {
        return mPhotosJsonBean;
    }

    public void setPhotosJsonBean(PhotosJsonBean photosJsonBean) {
        mPhotosJsonBean = photosJsonBean;
    }
}
