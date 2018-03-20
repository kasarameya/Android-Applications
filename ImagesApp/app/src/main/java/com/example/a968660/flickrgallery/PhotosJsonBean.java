package com.example.a968660.flickrgallery;

import java.util.ArrayList;
import java.util.List;

/**
 * FlickrGallery
 * <p>
 *
 * <br/>
 * <b>-------Class Description-------------------</b>
 * <br/>
 */

public class PhotosJsonBean {
    private int page;
    private List<GalleryBean> photo;

    public PhotosJsonBean() {
        photo = new ArrayList<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<GalleryBean> getPhoto() {
        return photo;
    }

    public void setPhoto(List<GalleryBean> photo) {
        this.photo = photo;
    }
}
