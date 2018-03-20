package com.example.a968660.flickrgallery;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

public class DataDownloader {
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static String API_KEY = "daced810ccafa2231d916e9a3e9c3d81";
    private List<GalleryBean> mGalleryBeanList = new ArrayList<>();
    private Uri mURI = Uri.parse("https://api.flickr.com/services/rest/").buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();
    public byte[] getUrlBytes(String mUrl){
        try {
            URL url = new URL(mUrl);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                InputStream inputStream = httpURLConnection.getInputStream();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    int bytesRead = 0;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = inputStream.read(buffer)) > 0){
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    httpURLConnection.disconnect();
                    return  outputStream.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUrlString(String mUrl){
        return new String(getUrlBytes(mUrl));
    }
    public List<GalleryBean> getItemsFromFlickr(String url){
        String jsonResponse = getUrlString(url);
        try {
            JSONObject jsonBody = new JSONObject(jsonResponse);
            Log.d("kk", "getItemsFromFlickr: "+jsonResponse);
            //parseItems(mGalleryBeanList,jsonBody);
            Gson gson = new GsonBuilder().create();
            EntireJsonModel entireJsonModel = gson.fromJson(jsonResponse, EntireJsonModel.class);

            return entireJsonModel.getPhotosJsonBean().getPhoto();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseItems(List<GalleryBean> galleryBeanList, JSONObject jsonBody){
        try {

            JSONObject photosJson = jsonBody.getJSONObject("photos");
            JSONArray photoJsonArray = photosJson.getJSONArray("photo");

            for (int i = 0; i < photoJsonArray.length(); i++){
                JSONObject jsonObject = photoJsonArray.getJSONObject(i);

                GalleryBean galleryBean = new GalleryBean();
                galleryBean.setId(jsonObject.getString("id"));
                galleryBean.setCaption(jsonObject.getString("title"));
                if (jsonObject.has("url_s"))
                    galleryBean.setUrl(jsonObject.getString("url_s"));
                galleryBeanList.add(galleryBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String buildUrl(String method, String query){
        Uri.Builder builder = mURI.buildUpon().appendQueryParameter("method", method);
        if (method.equalsIgnoreCase(SEARCH_METHOD)){
            builder.appendQueryParameter("text",query);
        }
        return builder.build().toString();

    }
    public List<GalleryBean> fetchPhotos(){
        return getItemsFromFlickr(buildUrl(FETCH_RECENTS_METHOD,null));
    }
    public List<GalleryBean> searchPhotos(String query){
        return getItemsFromFlickr(buildUrl(FETCH_RECENTS_METHOD,query));
    }
}
